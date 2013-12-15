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
	
	static transients = ["uniqueAddress"]
	static belongsTo = [client:Client]
	static hasMany = [wedTables:WedTable,partyGuests:PartyGuest]

	Long id
	String name
	Client client

	String toString() {
		return this.name
	}

	static Long getUniqueAddress(Long partyId) {
		def  c = PartyGuest.createCriteria()
		def res = c.listDistinct{

			party{
				eq('id',Long.valueOf(partyId))
			}
			guest{
				eq("isGuest",false)
			}

			projections {
				guest{
					property("address1")
					property("address2")
					property("city")
					property("state")
					property("zip")
					groupProperty("address1")
					groupProperty("address2")
					groupProperty("city")
					groupProperty("state")
					groupProperty("zip")
				}

			}

		}
		return res.size()
	}
	
	static Long getGuestCount(Long partyId) {
		def  c = PartyGuest.createCriteria()
		def res = c.get{
			party{
				eq('id',Long.valueOf(partyId))
			}
			guest{
				eq("isGuest",true)
			}
			projections {
				countDistinct "guest"
			}
		}
		return res
	}
}