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

import org.grails.plugins.excelimport.*
import org.apache.poi.ss.usermodel.*

class GuestImporter extends AbstractExcelImporter {
	
	static Map CONFIG_BOOK_COLUMN_MAP = [
		sheet:'Guests',
		startRow: 1,
		columnMap:  [
				'A': 'preFix',
				'B':'firstName',
				'C': 'middleName',
				'D':'lastName',
				'E':'address1',
				'F':'address2',
				'G':'city',
				'H':'state',
				'I':'zip',
				'J':'phone',
				'K':'guestEmail',
				'L':'isGuest',
				'M' :'party'		
		]
]
	
	static Map Config_Guest_Type = [
		preFix:([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
		firstName:([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
		middleName:([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
		lastName:([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
		address1:([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
		address2:([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
		city:([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
		state:([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
		zip:([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
		phone:([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
		guestEmail:([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
		isGuest:([expectedType: ExpectedPropertyType.IntType, defaultValue:null]),
		party:([expectedType: ExpectedPropertyType.StringType, defaultValue:null])
	]
	
	public GuestImporter() {
		super()
	}
	
	
	def excelImportService = new ExcelImportService()

	def getGuests() {
		return excelImportService.columns(workbook, CONFIG_BOOK_COLUMN_MAP,null,Config_Guest_Type) 
	}

	Map getOneMoreBookParams() { 
		Map bookParams = excelImportService.cells(workbook, CONFIG_BOOK_CELL_MAP ) 
	}
}