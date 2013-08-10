package com.muhlsoftware.wedding

class Party {

    static constraints = {
		name(nullable:false,blank:false,unique:['client'],validator: {val, obj ->
			return  !Party.findByNameIlikeAndIdNotEqual(val,obj?.id)
		})
    }
	
	static mapping = {
		version false
		autoTimestamp false
		cache true
		sort "name"
		id generator: 'hilo',
		   params: [table: 'party_hi_value', column: 'next_value', max_lo: 1]
	}
	
	static belongsTo = [client:Client]
	static hasMany = [wedTables:WedTable,partyGuests:PartyGuest]
	
	Long id
	String name
	Client client
	
	String toString() {
		return this.name
	}
}