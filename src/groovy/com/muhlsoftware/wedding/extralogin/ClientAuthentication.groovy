package com.muhlsoftware.wedding.extralogin

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

class ClientAuthentication extends UsernamePasswordAuthenticationToken {

	private static final long serialVersionUID = 1

	final String ClientId

	ClientAuthentication(principal, credentials, String clientId) {
		super(principal, credentials)
		ClientId = clientId
	}

	ClientAuthentication(principal, credentials, String clientId, Collection authorities) {
		super(principal, credentials, authorities)
		ClientId = clientId
	}
	
	String getClientId() {
		return this.ClientId
	}
}
