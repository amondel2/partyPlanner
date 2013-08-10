package com.muhlsoftware.wedding

class PartyGuest {

     static constraints = {
		party(nullable:false,blank:false,unique:['guest','client'])
		guest(nullable:false,blank:false,unique:['party','client'])
		entreeChoice(nullable:true)
		gift(nullable:true)
		thankYouCardSent(nullable:true)
		isAttending(nullable:true)
		seat(nullable:true,validator: {val, obj ->
			if (val) {
				Seat curr = Seat.findById(val?.id)
				if(curr?.partyGuest && curr?.partyGuest?.id != obj?.id) {
					return ['invalid.seatTaken',curr?.partyGuest?.guest.toString()]
				}
			}
		})
    }
	
	static mapping = {
		version false
		autoTimestamp false
		cache true
		id generator: 'hilo',
		   params: [table: 'party_guest_hi_value', column: 'next_value', max_lo: 1]
	}
	
	static belongsTo = [party:Party,guest:Guest,client:Client]
	
	Long id
	Party party
	Guest guest
	Client client
	String entreeChoice
	String gift
	Boolean thankYouCardSent
	Boolean isAttending
	Seat seat
	
	String toString() {
		return this.party.toString() + ' : ' + this.guest.toString()
	}	
}