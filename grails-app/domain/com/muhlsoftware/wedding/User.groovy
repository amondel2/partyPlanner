package com.muhlsoftware.wedding

class User {

	String username
	String password
	
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	Client client

	static belongsTo = [client:Client]
		
	static constraints = {
		username blank: false, unique: ['client']
		password blank: false
		client blank:false, nullable: false
	}

	static mapping = {
		password column: '`password`'
		cache false
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}
}