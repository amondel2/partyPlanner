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
class PartyEntreeController {
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
		
		params.max = Math.min(max ?: 10, 100)
		def c = PartyEntree.createCriteria()
		def results = c.list (params) {
			if(PartyEntree.hasProperty("client")) {
				ClientAuthentication auth = SecurityContextHolder.getContext().getAuthentication();
				def client = Client.findById(auth.getClientId())
				eq("client":client) 
			}
		}
		
       
        [partyEntreeInstanceList: results, partyEntreeInstanceTotal:results.totalCount]
    }

    def create() {
		ClientAuthentication auth = SecurityContextHolder.getContext().getAuthentication();
        [partyEntreeInstance: new PartyEntree(params),'auth':auth]
    }
	
    def save() {
		ClientAuthentication auth = SecurityContextHolder.getContext().getAuthentication();
        def partyEntreeInstance = new PartyEntree(params)
        if (!partyEntreeInstance.save(flush: true)) {
			if(partyEntreeInstance instanceof String) {
				partyEntreeInstance = partyEntreeInstance
			}
            render(view: "create", model: [partyEntreeInstance: partyEntreeInstance,'auth':auth])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'partyEntree.label', default: 'PartyEntree'), partyEntreeInstance.id])
        redirect(action: "show", id: partyEntreeInstance.id)
    }

    def show(Long id) {
        def partyEntreeInstance = PartyEntree.get(id)
        if (!partyEntreeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'partyEntree.label', default: 'PartyEntree'), id])
            redirect(action: "list")
            return
        }

        [partyEntreeInstance: partyEntreeInstance]
    }

    def edit(Long id) {
		ClientAuthentication auth = SecurityContextHolder.getContext().getAuthentication();
        def partyEntreeInstance = PartyEntree.get(id)
        if (!partyEntreeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'partyEntree.label', default: 'PartyEntree'), id])
            redirect(action: "list")
            return
        }

        [partyEntreeInstance: partyEntreeInstance, 'auth':auth]
    }

    def update(Long id, Long version) {
        def partyEntreeInstance = PartyEntree.get(id)
        if (!partyEntreeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'partyEntree.label', default: 'PartyEntree'), id])
            redirect(action: "list")
            return
        }
		
		ClientAuthentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (version != null) {
            if (partyEntreeInstance.version > version) {
                partyEntreeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'partyEntree.label', default: 'PartyEntree')] as Object[],
                          "Another user has updated this PartyEntree while you were editing")
                render(view: "edit", model: [partyEntreeInstance: partyEntreeInstance,'auth':auth])
                return
            }
        }

        partyEntreeInstance.properties = params

        if (!partyEntreeInstance.save(flush: true)) {
            render(view: "edit", model: [partyEntreeInstance: partyEntreeInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'partyEntree.label', default: 'PartyEntree'), partyEntreeInstance.id])
        redirect(action: "show", id: partyEntreeInstance.id)
    }

    def delete(Long id) {
        def partyEntreeInstance = PartyEntree.get(id)
        if (!partyEntreeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'partyEntree.label', default: 'PartyEntree'), id])
            redirect(action: "list")
            return
        }

        try {
            partyEntreeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'partyEntree.label', default: 'PartyEntree'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'partyEntree.label', default: 'PartyEntree'), id])
            redirect(action: "show", id: id)
        }
    }
}
