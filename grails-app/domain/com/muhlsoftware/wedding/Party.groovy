package com.muhlsoftware.wedding

class Party {

    static constraints = {
		name(nullable:false,blank:false,unique:true,validator: {val, obj ->
			return  !Party.findByNameIlikeAndIdNotEqual(val,obj?.id)
		})
    }
	
	static mapping = {
		version false
		autoTimestamp false
		cache true
		sort "name"
		id generator: 'hilo',
		   params: [table: 'guest_hi_value', column: 'next_value', max_lo: 1]
	}
	
	static hasMany = [wedTables:WedTable,partyGuests:PartyGuest]
	
	Long id
	String name
	
	String toString() {
		return this.name
	}
}