package com.muhlsoftware.wedding

import grails.plugins.springsecurity.Secured
@Secured(['ROLE_ADMIN'])
class PartyGuestController {

    static scaffold = true
	def index() { redirect(action:list) }
}
