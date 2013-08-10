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

class Guest implements Serializable {

    static constraints = {
		firstName(nullable:false,blank:false,unique:['middleName','lastName', 'client'])
		middleName(nullable:true)
		lastName(nullable:false,blank:false,unique:['middleName','firstName', 'client'])
		address1(nullable:true)
		address2(nullable:true)
		city(nullable:true)
		state(nullable:true)
		zip(nullable:true)
		phone(nullable:true)
		guestEmail(nullable:true)
		isGuest(nullable:true)
    }
	
	static mapping = {
		version false
		autoTimestamp false
		cache true
		sort "lastName"
		id generator: 'hilo',
		   params: [table: 'guest_hi_value', column: 'next_value', max_lo: 1]
	}
	
	static belongsTo = [client:Client]
	static hasMany = [partyGuests:PartyGuest]
	
	Long id
	Client client
	String firstName
	String middleName
	String lastName
	String address1
	String address2
	String city
	String state
	String zip
	String phone
	String guestEmail
	Boolean isGuest
	
	
	String toString() {
		return this.firstName + (this.middleName ? " " + this.middleName : '' ) + " " + this.lastName 	
	}
}
