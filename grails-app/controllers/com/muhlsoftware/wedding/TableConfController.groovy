/*******************************************************************************
 Party Planner web application for guest seat assignments and entree choices
	Copyright (C) 2012  Aaron Mondelblatt

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program.  If not, see http://www.gnu.org/licenses/gpl-3.0.txt.
***********************************************************************************/
package com.muhlsoftware.wedding
import org.codehaus.groovy.grails.commons.GrailsClassUtils
import org.junit.internal.runners.statements.FailOnTimeout;
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN','ROLE_USER'])
class TableConfController {

    def index() { 
		def c = Guest.createCriteria()
		def results = c{
			and{
				isNull('seat')
			   order('lastName','asc')
			   order('firstName','asc')
			   order('middleName','asc')
			}
		}
		render (view:"index",model:[tables:WedTable.list(),guests:results])
	}
	
	def getListSort(){
		def c = Guest.createCriteria()
		def results = c{
			and{
				isNull('seat')
			   order('lastName','asc')
			   order('firstName','asc')
			   order('middleName','asc')
			}
		}
		render (template:"guestList",model:[guests:results] )
	}
	
	def editUser() {
		def g = Guest.findById(params?.id)
		GrailsClassUtils.getPropertiesOfType(Guest,Boolean)?.each{ p->
			g.putAt(p.name,false) 
		}
		params?.each{ p ->
			def name = p.key
			if(g.hasProperty(name) && name != 'id' && name != 'version') { 
				def v =  p.value
				if(GrailsClassUtils.isPropertyOfType(Guest, name,Long)) {
					v = Long.valueOf(v)
				} else if ( GrailsClassUtils.isPropertyOfType(Guest, name,Boolean) ) {
					if(v == "on") {
						v = true
					} 
					v = Boolean.valueOf(v)
				}
				
				if ( name != "guestOf") { 
					g.putAt(name, v)
				} else {
					def ng
					if(v.id && v.id != null && v.id != 'null') {
						ng = Guest.findById(v.id)
					}
					if(ng) {
						g.putAt(name, ng)
					}
				}
			} 
		}
		g.save(flush:true,,failOnError:true)
		render (template:"user",model:[guest:g])  
	}
	
	def saveUser(){
		def g = new Guest()
		params?.each{ p ->
			def name = p.key
			if(g.hasProperty(name) && name != 'id' && name != 'version') {
				def v =  p.value
				if(GrailsClassUtils.isPropertyOfType(Guest, name,Long)) {
					v = Long.valueOf(v)
				} else if ( GrailsClassUtils.isPropertyOfType(Guest, name,Boolean) ) {
					if(v == "on") {
						v = true
					}
					v = Boolean.valueOf(v)
				}
				
				if ( name != "guestOf") {
					g.putAt(name, v)
				} else {
					def ng
					if(v.id && v.id != null && v.id != 'null') {
						ng = Guest.findById(v.id)
					}
					if(ng) {
						g.putAt(name, ng)
					}
				}
			}
		}
		g.save(flush:true,,failOnError:true)
		render (template:"user",model:[guest:g])
	}
	
	def tableDrop(){
		def t = WedTable.findById(params?.tableId)
		t.horzOffset = Double.valueOf(params?.top)
		t.verOffset = Double.valueOf(params.left)
		t.save(flush:true,,failOnError:true)
		render(text:"success")
	}
	
	def  delGuest(){
		def g = Guest.findById(params?.guestId);
		g.seat?.guest = null;
		g.seat?.save(flush:true,,failOnError:true)
		Seat?.findByGuest(g)?.each{
			it.guest = null
			it.save(flush: true,failOnError:true)
		}
		g.seat = null
		g.delete(flush: true)
		render(text:"success")
	}
	
	def addSeat() {
		def table = WedTable.findById(params?.tableId)
		def s = new Seat()
		def seats = table?.seats?.sort{a,b -> a.seatNumber > b.seatNumber ? 0 : 1 }
		s.seatNumber = seats?.get(0)?.seatNumber + 1 ?: 1
		s.wedTable = table
		s.save(flush:true,failOnError:true)
		render (template :"seat",model:[s:s]) 
	}
	
	def delSeat() {
		def s = Seat.findById(params?.seatId)
		if(s?.guest?.id){
			def g = s?.guest
			s.guest = null
			g?.seat = null
			g.save(flush: true,failOnError:true)
		}
		s.delete(flush: true)
		render(text:"success")
	}
	
	
	def sitDown() {
		def s = Seat.findById(params?.seatId)
		if(s.guest) {
			render(text:"Already There");
		} else {
			def g = Guest.findById(params?.guestId)
			if(g.seat) {
				g.seat?.guest = null
				g.seat.save(flush:true);
			}
			s?.guest = g
			g?.seat = s
			g?.save(flush: true,failOnError:true)
			s?.save(flush: true,failOnError:true)
			render(text:"success")
		}
	}
	
	def standUp() {
		def g = Guest.findById(params?.guestId)
		//findAnySeat with this guest
		Seat?.findByGuest(g)?.each{
			it.guest = null
			it.save(flush: true,failOnError:true)
		}
		g?.seat = null
		g?.save(flush: true,failOnError:true)
		render(text:"success")
	}
	
	def delTable() {
		def t = WedTable.findById(params?.tableId)
		t.seats?.each { s ->
			if(s?.guest?.id){
				def g = s?.guest
				s.guest = null
				g?.seat = null
				g.save(flush: true,failOnError:true)
			}
		}
		t.delete(flush: true)
		render(text:"success")
	}
	
	def addTable() {
		def t = new WedTable()
		t.name = params?.tableName
		def s = new Seat()
		s.seatNumber = 1
		s.wedTable = t
		t.seats = [s]
		t.save(flush: true,failOnError:true)
		s.save(flust:true,failOnError:true)
		render (template :"table",model:[t:t]) 
	}
}
