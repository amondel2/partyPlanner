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
import org.grails.datastore.mapping.collection.PersistentSet
import org.junit.internal.runners.statements.FailOnTimeout;
import grails.converters.JSON
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_CLIENT_ADMIN','ROLE_USER'])
class TableConfController {

	def tableConfService
	def exportService

	def index() {
		def partyId = Long.valueOf(params?.id)
		session['partyId'] = partyId
		def results = tableConfService.getSortedGuestList(Long.valueOf(session['partyId']))
		Party party = Party.findById(partyId)
		def tables = party?.wedTables
		def entrees = party?.partyEntrees
		def vendors = party?.partyVendors
		render (view:"index",model:[tables:tables,guests:results,partyId:partyId,entrees:entrees,vendors:vendors])
	}

	def getListSort(){
		def results = tableConfService.getSortedGuestList(Long.valueOf(session['partyId']))
		render (template:"guestList",model:[guests:results] )
	}

	def editUser() {
		def g = tableConfService.editPartyGuest(params)
		render (template:"user",model:[guest:g])
	}

	def saveUser(){
		def msg
		try{
			msg = tableConfService.savePartyGuest(params)
		} catch(Exception e) {
			msg = ['status': 'FAILURE', "msg": "Error: " + e.getMessage()]
		}
		render msg as JSON
	}
	
	def getAddressCount(){
		def msg
		try{ 
			def countMe = Party.getUniqueAddress(Long.valueOf(session['partyId']))
			msg = ['status': 'SUCCESS', "Count" : countMe]
		} catch(Exception e) {
			msg = ['status': 'FAILURE', "msg": "Error: " + e.getMessage()]
		}
		render msg as JSON
	}
	
	def getGuestCount() {
		def msg
		try{
			msg = ['status': 'SUCCESS', "Count" : Party.getGuestCount(Long.valueOf(session['partyId']))]
		} catch(Exception e) {
			msg = ['status': 'FAILURE', "msg": "Error: " + e.getMessage()]
		}
		render msg as JSON
		
	}
	
	def giftExport() {
		try{
			def partyGuestList = tableConfService.getGiftListForParty(Long.valueOf(session['partyId']))
			exportService.exportGiftList(response,partyGuestList)
		} catch(Exception e) {
			render( ['status': 'FAILURE', "msg": "Error: " + e.getMessage()] as JSON)
		}
	}
	
	def getEntreeCount() {
		def msg
		try{
			Party party = Party.findById(Long.valueOf(session['partyId']))
			def vendorFood = 0;
			party?.partyVendors.each{
				vendorFood += it.getMealCost()
			}
			msg = ['status': 'SUCCESS', "Count" : tableConfService.getEntreeCount(Long.valueOf(session['partyId'])),"vendorFoodCost":vendorFood]
		} catch(Exception e) {
			msg = ['status': 'FAILURE', "msg": "Error: " + e.getMessage()]
		}
		render msg as JSON
	}
	
	def getVendorCost(){
		def msg
		try{
			Party party = Party.findById(Long.valueOf(session['partyId']))
			def vendorCost = 0
			def vedorPaid = 0
			party?.partyVendors.each{
				vendorCost += ( it.cost ?: 0 )
				vedorPaid +=  ( it.paid ?: 0 )
			}
			msg = ['status': 'SUCCESS', "vedorPaid" : vedorPaid,"vendorCost":vendorCost]
		} catch(Exception e) {
			msg = ['status': 'FAILURE', "msg": "Error: " + e.getMessage()]
		}
		render msg as JSON
	}

	def tableDrop(){
		try{
			def t = WedTable.findById(params?.tableId)
			t.horzOffset = Double.valueOf(params?.top)
			t.verOffset = Double.valueOf(params.left)
			t.save(flush:true,,failOnError:true)
			render(text:"success")
		} catch(Exception e) {
			render(text:"ERROR " + e.getMessage())
		}
	}

	def  delGuest(){
		try{
			tableConfService.removePartyGuest(params?.guestId)
			render(text:"success")
		} catch(Exception e) {
			render(text:"ERROR " + e.getMessage())
		}

	}

	def addSeat() {
		render (template :"seat",model:[s:tableConfService.addSeatToTable(params)])
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
		def status
		try{
			status = tableConfService.addGuestToSeat(params?.guestId,params?.seatId)
		} catch(Exception e) {
			render(text:"ERROR " + e.getMessage())
		}
		render(text:status)
	}

	def standUp() {
		def status
		try{
			status = tableConfService.removeGuestFromSeat(params?.guestId)
		} catch(Exception e) {
			render(text:"ERROR " + e.getMessage())
		}
		render(text:status)
		
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
		t.party = p
		def s = new Seat()
		s.seatNumber = 1
		s.wedTable = t
		t.party = p
		t.seats = [s]
		def seatList = [s]
		t.save(flush: true,failOnError:true)
		s.save(flust:true,failOnError:true)
		def c = 2
		def totalSeats = params.seatTotal?.toInteger()
		while(c++ <= totalSeats) {
			def s2 = tableConfService.addSeatToTable(params,t)
			t.seats.add(s2)
			seatList.add(s2)
		}
		t.seats =  seatList.sort{a,b -> a.seatNumber < b.seatNumber ? 0 : 1 }
		render (template :"table",model:[t:t,seatList:seatList])
	}
	
	def editTableName(){
		def status
		try{
			status = tableConfService.editTableName(params.tableId,params.tableName)
		} catch(Exception e) {
			render(text:"ERROR " + e.getMessage())
		}
		render(text:status)
	}
}
