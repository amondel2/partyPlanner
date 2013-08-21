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
import com.muhlsoftware.wedding.extralogin.ClientAuthentication
import grails.plugins.springsecurity.Secured
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Secured(['ROLE_CLIENT_ADMIN'])
class ImportGuestController {

	def importGuestService

	def index() {
		ClientAuthentication auth = SecurityContextHolder.getContext().getAuthentication();
		def client = Client.findById(auth.getClientId())
		render(view:"import")
	}

	def upload() {
		ClientAuthentication auth = SecurityContextHolder.getContext().getAuthentication();
		def client = Client.findById(auth.getClientId())
		def p = request.getFile('excel')
		def msg
		if(p && p?.size > 0 && p?.fileItem?.fileName?.trim().size() > 0 ) {
			try{
				msg = importGuestService.uploadGuest(client,p)
				render(view:"import",model:[msg:msg])
			} catch (Exception e) {
				msg = "Error During Save: " +  e.getMessage()
			}
		} else if (params?.up == "up") {
			msg = "no File"
		}
		render(view:"import",model:[msg:msg])
	}
}