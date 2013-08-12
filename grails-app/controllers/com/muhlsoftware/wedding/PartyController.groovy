package com.muhlsoftware.wedding

import grails.plugins.springsecurity.Secured
import pl.touk.excel.export.WebXlsxExporter
@Secured(['ROLE_CLIENT_ADMIN'])
class PartyController {
	static scaffold = true
	def index() { redirect(action:list) }
	
	def exportGuestToExcel() {
		def headers = ['First', 'Middle', 'Last', 'Address1', 'Address2', 'City', 'State', 'Zip', 'Phone', 'E-mail']

		def withProperties = ['firstName', 'middleName', 'lastName', 'address1', 'address2', 'city', 'state', 'zip', 'phone', 'guestEmail']
	  
		def party = Party.findById(params?.id) 
		
		def partyGuest = PartyGuest.findAllByParty(party)
		
		def guest = partyGuest.collect{it.guest}
		
		new WebXlsxExporter().with {
			setResponseHeaders(response)
			fillHeader(headers)
			add(guest, withProperties)
			save(response.outputStream)
		}
	}
}