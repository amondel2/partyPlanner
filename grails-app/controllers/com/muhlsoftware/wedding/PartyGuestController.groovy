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

@Secured(['ROLE_CLIENT_ADMIN'])
class PartyGuestController {
	static scaffold = true
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index() {
		redirect(action: "list", params: params)
	}

	def list(Integer max) {

		params.max = Math.min(max ?: 10, 100)
		def c = PartyGuest.createCriteria()
		def results = c.list (params) {
			if(PartyGuest.hasProperty("client")) {
				ClientAuthentication auth = SecurityContextHolder.getContext().getAuthentication();
				def client = Client.findById(auth.getClientId())
				eq("client":client)
			}
		}


		[partyGuestInstanceList: results, partyGuestInstanceTotal:results.totalCount]
	}

	def create() {
		ClientAuthentication auth = SecurityContextHolder.getContext().getAuthentication();
		def pgi = null
		try{
			if(params.partyId) {
				def client = Client.findById(auth.getClientId())
				if(client) {
					pgi = Party.findByIdAndClient(Long.valueOf(params.partyId),client)
				}
			}
		} catch (Exception e) {}
		[partyGuestInstance: new PartyGuest(params),'auth':auth,partyInstance:pgi]
	}

	def save() {
		ClientAuthentication auth = SecurityContextHolder.getContext().getAuthentication();
		def partyGuestInstance = new PartyGuest(params)
		if (!partyGuestInstance.save(flush: true)) {
			if(partyGuestInstance instanceof String) {
				partyGuestInstance = partyGuestInstance
			}
			render(view: "create", model: [partyGuestInstance: partyGuestInstance,'auth':auth])
			return
		}

		flash.message = message(code: 'default.created.message', args: [message(code: 'partyGuest.label', default: 'PartyGuest'), partyGuestInstance.id])
		redirect(action: "show", id: partyGuestInstance.id)
	}

	def show(Long id) {
		def partyGuestInstance = PartyGuest.get(id)
		if (!partyGuestInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'partyGuest.label', default: 'PartyGuest'), id])
			redirect(action: "list")
			return
		}

		[partyGuestInstance: partyGuestInstance]
	}

	def edit(Long id) {
		ClientAuthentication auth = SecurityContextHolder.getContext().getAuthentication();
		def partyGuestInstance = PartyGuest.get(id)
		if (!partyGuestInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'partyGuest.label', default: 'PartyGuest'), id])
			redirect(action: "list")
			return
		}

		[partyGuestInstance: partyGuestInstance, 'auth':auth]
	}

	def update(Long id, Long version) {
		def partyGuestInstance = PartyGuest.get(id)
		if (!partyGuestInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'partyGuest.label', default: 'PartyGuest'), id])
			redirect(action: "list")
			return
		}

		ClientAuthentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (version != null) {
			if (partyGuestInstance.version > version) {
				partyGuestInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						[message(code: 'partyGuest.label', default: 'PartyGuest')] as Object[],
						"Another user has updated this PartyGuest while you were editing")
				render(view: "edit", model: [partyGuestInstance: partyGuestInstance,'auth':auth])
				return
			}
		}

		partyGuestInstance.properties = params

		if (!partyGuestInstance.save(flush: true)) {
			render(view: "edit", model: [partyGuestInstance: partyGuestInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [message(code: 'partyGuest.label', default: 'PartyGuest'), partyGuestInstance.id])
		redirect(action: "show", id: partyGuestInstance.id)
	}

	def delete(Long id) {
		def partyGuestInstance = PartyGuest.get(id)
		if (!partyGuestInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'partyGuest.label', default: 'PartyGuest'), id])
			redirect(action: "list")
			return
		}

		try {
			partyGuestInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'partyGuest.label', default: 'PartyGuest'), id])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'partyGuest.label', default: 'PartyGuest'), id])
			redirect(action: "show", id: id)
		}
	}
}
