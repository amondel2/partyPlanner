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
		firstName(nullable:false,blank:false,unique:['middleName','lastName'])
		middleName(nullable:true)
		lastName(nullable:false,blank:false,unique:['middleName','firstName'])
		address1(nullable:true)
		address2(nullable:true)
		city(nullable:true)
		state(nullable:true)
		zip(nullable:true)
		phone(nullable:true)
		entreeChoice(nullable:true)
		gift(nullable:true)
		guestEmail(nullable:true)
		thankYouCardSent(nullable:true)
		isAttending(nullable:true)
		isGuest(nullable:true)
		seat(nullable:true,validator: {val, obj ->
            if (val) {
				def curr = Seat.findById(val?.id)
				if(curr?.guest && curr?.guest?.id != obj?.id) {
					return ['invalid.seatTaken',curr?.guest?.toString()]
				}
            }
        })
		guestOf(nullable:true,validator: {val, obj ->
            if (val && val?.id == obj.id) return ['invlalid.guestOf']
        })
    }
	
	static mapping = {
		version false
		autoTimestamp false
		cache true
		sort "lastName"
		id generator: 'hilo',
		   params: [table: 'guest_hi_value', column: 'next_value', max_lo: 1]
	}
	
	static belongsTo = [guestOf:Guest,seat:Seat]
	
	Long id
	String firstName
	String middleName
	String lastName
	String address1
	String address2
	String city
	String state
	String zip
	String phone
	String entreeChoice
	String gift
	String guestEmail
	Boolean thankYouCardSent
	Boolean isAttending
	Boolean isGuest
	Guest guestOf
	Seat seat
	
	String toString() {
		return this.firstName + (this.middleName ? " " + this.middleName : '' ) + " " + this.lastName 	
	}
}
