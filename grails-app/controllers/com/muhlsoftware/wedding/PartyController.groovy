package com.muhlsoftware.wedding

import grails.plugins.springsecurity.Secured
@Secured(['ROLE_CLIENT_ADMIN'])
class PartyController {
	static scaffold = true
	def index() { redirect(action:list) }
}