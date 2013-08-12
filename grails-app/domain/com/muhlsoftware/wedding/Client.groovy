package com.muhlsoftware.wedding
import java.util.regex.Matcher
import java.util.regex.Pattern



class Client {

   static constraints = {
		name(nullable:false,blank:false,unique:true,,validator: {val, obj ->
			if (val) {
				val = val?.trim()
				def pattern = ~/[\p{L}\p{Nd}]+/
				if(!pattern.matcher(val).matches()) {
					return ['invalid.client']
				}
			}
		})
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
	
	static hasMany = [guests:Guest,parties:Party,users:User]
	
	Long id
	String name
	Boolean active = true
	
	
	String toString() {
		return this.name 	
	}
}
