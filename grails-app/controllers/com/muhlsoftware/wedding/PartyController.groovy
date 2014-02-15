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
class PartyController {
	static scaffold = true
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def exportService
	
	def exportGuestToExcel() {

		def guests = PartyGuest.findAllByParty(Party.findById(params?.id))?.collect{it.guest}.sort{it.lastName}

		exportService.exportGuestItems(response,guests)
	}
	
    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
		
		params.max = Math.min(max ?: 10, 100)
		def c = Party.createCriteria()
		def results = c.list (params) {
			if(Party.hasProperty("client")) {
				ClientAuthentication auth = SecurityContextHolder.getContext().getAuthentication();
				def client = Client.findById(auth.getClientId())
				eq("client":client) 
			}
		}
		
       
        [partyInstanceList: results, partyInstanceTotal:results.totalCount]
    }

    def create() {
		ClientAuthentication auth = SecurityContextHolder.getContext().getAuthentication();
        [partyInstance: new Party(params),'auth':auth]
    }
	
    def save() {
		ClientAuthentication auth = SecurityContextHolder.getContext().getAuthentication();
        def partyInstance = new Party(params)
        if (!partyInstance.save(flush: true)) {
			if(partyInstance instanceof String) {
				partyInstance = partyInstance
			}
            render(view: "create", model: [partyInstance: partyInstance,'auth':auth])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'party.label', default: 'Party'), partyInstance.id])
        redirect(action: "show", id: partyInstance.id)
    }

    def show(Long id) {
        def partyInstance = Party.get(id)
        if (!partyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'party.label', default: 'Party'), id])
            redirect(action: "list")
            return
        }

        [partyInstance: partyInstance]
    }

    def edit(Long id) {
		ClientAuthentication auth = SecurityContextHolder.getContext().getAuthentication();
        def partyInstance = Party.get(id)
        if (!partyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'party.label', default: 'Party'), id])
            redirect(action: "list")
            return
        }

        [partyInstance: partyInstance, 'auth':auth]
    }

    def update(Long id, Long version) {
        def partyInstance = Party.get(id)
        if (!partyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'party.label', default: 'Party'), id])
            redirect(action: "list")
            return
        }
		
		ClientAuthentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (version != null) {
            if (partyInstance.version > version) {
                partyInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'party.label', default: 'Party')] as Object[],
                          "Another user has updated this Party while you were editing")
                render(view: "edit", model: [partyInstance: partyInstance,'auth':auth])
                return
            }
        }

        partyInstance.properties = params

        if (!partyInstance.save(flush: true)) {
            render(view: "edit", model: [partyInstance: partyInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'party.label', default: 'Party'), partyInstance.id])
        redirect(action: "show", id: partyInstance.id)
    }

    def delete(Long id) {
        def partyInstance = Party.get(id)
        if (!partyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'party.label', default: 'Party'), id])
            redirect(action: "list")
            return
        }

        try {
            partyInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'party.label', default: 'Party'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'party.label', default: 'Party'), id])
            redirect(action: "show", id: id)
        }
    }
}
