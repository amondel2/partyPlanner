package com.muhlsoftware.wedding

class Client {

   static constraints = {
		name(nullable:false,blank:false,unique:true)
   		active(nullable:false,blank:false)
    }
	
	static mapping = {
		version false
		autoTimestamp false
		cache true
		sort "name"
		id generator: 'hilo',
		   params: [table: 'client_hi_value', column: 'next_value', max_lo: 1]
	}
	
	static hasMany = [partyGuests:PartyGuest,guests:Guest,parties:Party,users:User]
	
	Long id
	String name
	Boolean active = true
	
	
	String toString() {
		return this.name 	
	}
}
