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
import grails.converters.JSON
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN','ROLE_USER'])
class TableConfController {

    def index() {
		def c = PartyGuest.createCriteria()
		def partyId = Long.valueOf(params?.id)
		session['partyId'] = partyId
		def results = c{
			party{
				eq('id',partyId)
			}
			guest{
				order('lastName','asc')
				order('firstName','asc')
				order('middleName','asc')
			}
			isNull('seat')
		}
		def tables = Party.findById(partyId)?.wedTables
		render (view:"index",model:[tables:tables,guests:results,partyId:partyId])
	}
	
	def getListSort(){
		def c = PartyGuest.createCriteria()
		def results = c{
			party{
				eq('id',Long.valueOf(session['partyId']))
			}
			guest{
				order('lastName','asc')
				order('firstName','asc')
				order('middleName','asc')
			}
			isNull('seat')
		}
		render (template:"guestList",model:[guests:results] )
	}
	
	def editUser() {
		def g = PartyGuest.findById(params?.id)
		GrailsClassUtils.getPropertiesOfType(PartyGuest,Boolean)?.each{ p->
			g.putAt(p.name,false) 
		}
		params?.each{ p ->
			def name = p.key
			if(g.hasProperty(name) && name != 'id' && name != 'version') { 
				def v =  p.value
				if(GrailsClassUtils.isPropertyOfType(PartyGuest, name,Long)) {
					v = Long.valueOf(v)
				} else if ( GrailsClassUtils.isPropertyOfType(PartyGuest, name,Boolean) ) {
					if(v == "on") {
						v = true
					} 
					v = Boolean.valueOf(v)
				}
				g.putAt(name, v)
			}
		}
		g.save(flush:true,,failOnError:true)
		render (template:"user",model:[guest:g])  
	}
	
	def saveUser(){
		def pa = params
		def guest =  Guest.findById(params?.guest['id'])
		def party = Party.findById(session['partyId'])
		if( PartyGuest.findByPartyAndGuest(party,guest) ) {
			def m = ['status': 'FAILURE', "msg": "Party Guest Already Exists"] 
			render m as JSON
			return
		}
		def newPG = new PartyGuest(party:party,guest:guest)
		GrailsClassUtils.getPropertiesOfType(PartyGuest,Boolean)?.each{ p->
			newPG.putAt(p.name,false)
		}
		params?.each{ p ->
			def name = p.key
			if(newPG.hasProperty(name) && name != 'id' && name != 'version' && name != 'guest') {
				def v =  p.value
				if(GrailsClassUtils.isPropertyOfType(PartyGuest, name,Long)) {
					v = Long.valueOf(v)
				} else if ( GrailsClassUtils.isPropertyOfType(PartyGuest, name,Boolean) ) {
					if(v == "on") {
						v = true
					}
					v = Boolean.valueOf(v)
				}
				
				newPG.putAt(name, v)
			}
		}
		newPG.save(flush:true,,failOnError:true)
		//associate this guest to the current party
		def m = ['status': 'SUCCESS', "msg": "SUCCESS"]
		render m as JSON
	}
	
	def tableDrop(){
		def t = WedTable.findById(params?.tableId)
		t.horzOffset = Double.valueOf(params?.top)
		t.verOffset = Double.valueOf(params.left)
		t.save(flush:true,,failOnError:true)
		render(text:"success")
	}
	
	def  delGuest(){
		def pg = PartyGuest.findById(params?.guestId)
		pg?.party.partyGuests = null
		pg?.guest.partyGuests = null
		pg?.party.save(flush: true,failOnError:true)
		pg?.guest.save(flush: true,failOnError:true)
		pg.seat?.partyGuest = null
		pg.seat?.save(flush:true,failOnError:true)
		def s = Seat.withCriteria {
			partyGuest{
				eq('id',pg?.id)
			}
		}
		s?.each{
			it.partyGuest = null
			it.save(flush: true,failOnError:true)
		}
		pg.seat = null
		pg.delete(flush: true)
		render(text:"success")
	}
	
	def addSeat() {
		def table = WedTable.findById(params?.tableId)
		
		def s = new Seat()
		def seats = table?.seats?.sort{b,a -> a.seatNumber <=> b.seatNumber}
		s.seatNumber = seats?.get(0)?.seatNumber + 1 ?: 1
		s.wedTable = table
		s.save(flush:true,failOnError:true)
		
	
		render (template :"seat",model:[s:s]) 
	}
	
	def delSeat() {
		def s = Seat.findById(params?.seatId)
		if(s?.partyGuest?.id){
			def g = s?.partyGuest
			s.partyGuest = null
			g?.seat = null
			g.save(flush: true,failOnError:true)
		}
		s.delete(flush: true)
		render(text:"success")
	}
	
	
	def sitDown() {
		def s = Seat.findById(params?.seatId)
		if(s.partyGuest) {
			render(text:"Already There");
		} else {
			def g = PartyGuest.findById(params?.guestId)
			if(g.seat) {
				g.seat?.partyGuest = null
				g.seat.save(flush:true);
			}
			s?.partyGuest = g
			g?.seat = s
			g?.save(flush: true,failOnError:true)
			s?.save(flush: true,failOnError:true)
			render(text:"success")
		}
	}
	
	def standUp() {
		def g = PartyGuest.findById(params?.guestId)
		//findAnySeat with this guest
		def s = Seat.withCriteria {
			partyGuest{
				eq('id',g?.id)
			}
		}
		s?.each{
			it.partyGuest = null
			it.save(flush: true,failOnError:true)
		}
		g?.seat = null
		g?.save(flush: true,failOnError:true)
		render(text:"success")
	}
	
	def delTable() {
		WedTable t = WedTable.findById(params?.tableId)
		t?.seats?.each { s ->
			if(s?.partyGuest?.id){
				PartyGuest g = s?.partyGuest
				s.partyGuest = null
				g?.seat = null
				g.save(flush: true,failOnError:true)
			}
		}
		t?.delete(flush: true)
		render(text:"success")
	}
	
	def addTable() {
		def t = new WedTable()
		def p = Party.findById(session['partyId'])
		t.name = params?.tableName
		def s = new Seat()
		s.seatNumber = 1
		s.wedTable = t
		t.party = p
		t.seats = [s]
		t.save(flush: true,failOnError:true)
		s.save(flust:true,failOnError:true)
		render (template :"table",model:[t:t]) 
	}
}
