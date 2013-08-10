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

class HtmlformatTagLib {
	static namespace = "wed"
	
	def formatGuestTitle =  { attrs, body ->
		 def str = ''
		 PartyGuest pg = attrs?.guest
		 Guest g = pg.guest
		 
		 if(g && g?.firstName) {
			if (pg?.isAttending) {
				str = ' IS COMMING AND EATING ' + pg.entreeChoice
		 	} else {
				str = ' NOT COMMING ' 
		    } 
			str += '<br>'
			if(g?.address1 || g?.address2  ) {
				str += 'Address: ' 
				if(g?.address1) {
					str += g?.address1  + '<br>'
				}
				if(g?.address2) {
					str += g?.address2  + '<br>'
				}				
			}
			if(g?.city || g?.state || g?.zip) {
				if(g?.city && g?.state) {
					str += g?.city + ', ' + g?.state + ' '
				} else if ( g?.city || g?.state) {
					str += g?.city ?: '' +  g?.state ?: '' + ' '
				} 
				if(g?.zip) {
					str += g?.zip
				}
				if (g?.phone) {
					str += '<br>Phone: ' + g?.phone
				}
			} else if (g?.phone) {
				str += '<br>Phone: ' + g?.phone
			} 
		 }
		 out << body() << str
	}
	
	def showPartyDropDown = { attrs, body ->
		def str = ''
		Party?.list().each{ p ->
			str += '<li><a class="partyTime" href="' + g.createLink([uri: '/tableConf/index/' + p.id]) + '">' + p?.toString() + '</a></li>'
		}
		out << body() << str
	}
	
	def getClientList = { attrs, body ->
		def str = ''
		Client?.findAllByActive(true) each{ p ->
			str += '<option value="' + p.id + '">' + p?.toString() + '</option>' + "\n"
		}
		out << body() << str
	}
}