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

import grails.converters.JSON

class ImportGuestService {

    def uploadGuest(client,p) {
		
		GuestImporter importer = new GuestImporter();
		importer.read(p?.getInputStream())
		def msg=""
		def items
		try {
			items = importer.getGuests()
		} catch (Exception e) {
			println e?.message
			msg += "Excel Sheet is not valid"
		}

		if(items && items?.size() > 0) {
			def newItems = 0
			def updatedItems = 0
			def row=1
			items.each{ item->
				def g = Guest.findByFirstNameAndMiddleNameAndLastNameAndClient(item?.firstName,item?.middleName,item?.lastName,client)
				def isupdate = true
				if(!g) {
					g = new Guest()
					isupdate = false
					g.client = client
				}
				def gpArray = []
				item.eachWithIndex { itemObj, i ->
					def itemName = itemObj?.key
					def itemValue = itemObj?.value
					if(g.hasProperty(itemName) || itemName == 'party') {
						if(itemName == 'isGuest') {
							if(itemValue > 0 ) {
								g.isGuest = true
							} else {
								g.isGuest = false
							}

						} else if (itemName == 'party' ) {
							itemValue = itemValue?.trim()
							def itemVarry = itemValue?.tokenize(',')
							if(itemValue?.size() == 0 || !itemVarry || itemVarry?.size() == 0) {
								//they won't be show up for any party but will be loaded into the system
							}  else {
								itemVarry.each{ party ->
									def partyObj = Party.findByNameIlikeAndClient(party?.trim(),client)
									if(partyObj){
										def guestParty
										if(isupdate) {
											guestParty = PartyGuest.findByGuestAndParty(g,partyObj)
										}
										if(!guestParty) {
											gpArray.add(partyObj)
										}
									}
								}
							}
						} else {
							g.putAt(itemName, itemValue)

						}

					}
				}
				if(g.validate()){
					isupdate ?  updatedItems++ : newItems++
					g.save(flush:true)
					gpArray.each{ partyObj ->
						def gp = new PartyGuest(guest:g,party:partyObj)
						gp.save(flush:true)
					}
				} else {
					g.errors.allErrors.each{
						msg += "line " + row + ": " + it?.toString() + "<br><br>"
					}
				}
				row++
			}
			msg += "Success! New Rows = " + newItems + " Updated Items = " + updatedItems
		} else if(msg?.size() == 0) {
			msg += "Nothing to do Rows"
		}
    }
	
	
}
