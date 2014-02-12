package com.muhlsoftware.wedding

class PartyVendor {
	
    static constraints = {
		party(nullable:false,blank:false)
		vendorName(nullable:false,unique:['party'])
		entree(nullable:true)
		cost(nullable:true)
		paid(nullable:true)
		numberOfPeopleMeals(nullable:true)
    }
	
	static mapping = {
		version false
		autoTimestamp false
		cache true
		id generator: 'hilo',
		   params: [table: 'party_vendor_hi_value', column: 'next_value', max_lo: 1]
	}
	
	static belongsTo = [party:Party,entree:PartyEntree]
	
	Long id
	Party party
	PartyEntree entree
	String vendorName
	Double cost
	Double paid
	Long numberOfPeopleMeals
	
	String toString() {
		return this.vendorName
	}
	
	Double getMealCost() {
		if(this.entree && this.numberOfPeopleMeals) {
			return this.entree.cost * (this.numberOfPeopleMeals ?: 0)
		} else {
			return 0
		}
	}
	
	Double getFullCost() {	
		return getMealCost() + ( this.cost ?: 0)
	}
	
	Double getMoneyOwed() {
		return getFullCost() - ( this.paid ?: 0)
	}
}