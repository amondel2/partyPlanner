package com.muhlsoftware.wedding

class PartyEntree {
	
    static constraints = {
		party(nullable:false,blank:false)
		entreeName(nullable:false,unique:['party'])
		cost(nullable:true)
    }
	
	static mapping = {
		version false
		autoTimestamp false
		cache true
		id generator: 'hilo',
		   params: [table: 'party_entree_hi_value', column: 'next_value', max_lo: 1]
	}
	
	static belongsTo = [party:Party]
	
	Long id
	Party party
	String entreeName
	Double cost
	
	String toString() {
		return this.entreeName
	}
}
