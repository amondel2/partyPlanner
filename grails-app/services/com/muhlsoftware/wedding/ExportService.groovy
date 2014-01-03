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

import pl.touk.excel.export.WebXlsxExporter

class ExportService {

    def getGuestHeader() {
		return ['PreFix', 'First', 'Middle', 'Last', 'Address1', 'Address2', 'City', 'State', 'Zip', 'Phone', 'E-mail','isGuest','party']
    }
	
	def getGuestProperties(){
		return ['preFix','firstName', 'middleName', 'lastName', 'address1', 'address2', 'city', 'state', 'zip', 'phone', 'guestEmail', new IsGuestGetter('isGuest'),new  PartyGetter('parties')]
    }
	
	def exportGuestItems(response,guestArray){
		new WebXlsxExporter().with {
			setResponseHeaders(response)
			fillHeader(this.getGuestHeader())
			add(guestArray, this.getGuestProperties())
			save(response.outputStream)
		}
	}
}
