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

import java.io.Serializable;

class Seat implements Serializable {

    static constraints = {
		seatNumber(nullable:false,unique:['wedTable'])
		partyGuest(nullable:true)
		wedTable(nullable:false)
		
    }
	
	static mapping = {
		version false
		autoTimestamp false
		cache true
		sort 'wedTable'
		id generator: 'hilo',
		   params: [table: 'seat_hi_value', column: 'next_value', max_lo: 1]
	}
	
	
	static belongsTo = [wedTable:WedTable,partyGuest:PartyGuest]
	
	Long id
	PartyGuest partyGuest
	Integer seatNumber
	WedTable wedTable
	
	String toString() {
		return this.wedTable?.toString() + " Seat Number: " + this.seatNumber?.toString()
	}
}
