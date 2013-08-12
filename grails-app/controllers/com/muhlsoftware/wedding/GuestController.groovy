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

import org.springframework.dao.DataIntegrityViolationException

import grails.plugins.springsecurity.Secured
import com.muhlsoftware.wedding.extralogin.ClientAuthentication
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.touk.excel.export.WebXlsxExporter
import pl.touk.excel.export.getters.PropertyGetter

class PartyGetter extends PropertyGetter<Guest, String> { 
	PartyGetter(String propertyName) {
		super(propertyName)
	}

	@Override
	protected String format(Guest value) {
		return value //you can do anything you like in here
	}
	
	@Override
	String getFormattedValue(Object object) {
		if(propertyName == null) {
			return null
		}
		def parties = object.partyGuests.collect{PartyGuest pg->
			pg.party.name
		}
		return parties && parties?.size() > 0 ? parties.join(',') : ' '
	}
}

class IsGuestGetter extends PropertyGetter<Guest, String> {
	IsGuestGetter(String propertyName) {
		super(propertyName)
	}

	@Override
	protected String format(Guest value) {
		return value //you can do anything you like in here
	}
	
	@Override
	String getFormattedValue(Object object) {
		if(propertyName == null) {
			return ''
		}
		if(object.getAt(propertyName)) {
			return '1'
		} else {
			return ''
		}
	}
}

@Secured(['ROLE_CLIENT_ADMIN'])
class GuestController {

	static scaffold = true
    def index() { redirect(action:list) }
	
	def exportGuestToExcel() {
				def headers = ['First', 'Middle', 'Last', 'Address1', 'Address2', 'City', 'State', 'Zip', 'Phone', 'E-mail','isGuest','party']
				def withProperties = ['firstName', 'middleName', 'lastName', 'address1', 'address2', 'city', 'state', 'zip', 'phone', 'guestEmail', new IsGuestGetter('isGuest'),new  PartyGetter('parties')]
				
				ClientAuthentication auth = SecurityContextHolder.getContext().getAuthentication();
				def client = Client.findById(auth.getClientId())				
				def guest  = Guest.findAllByClient(client)
				new WebXlsxExporter().with {
					setResponseHeaders(response)
					fillHeader(headers)
					add(guest, withProperties)
					save(response.outputStream)
				}
	}
}


