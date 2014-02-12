package com.muhlsoftware.wedding

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_CLIENT_ADMIN'])
class PartyVendorController {
	static scaffold = true
	def index() { redirect(action:list) }	     
}