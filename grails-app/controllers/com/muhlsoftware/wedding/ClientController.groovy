package com.muhlsoftware.wedding

import org.springframework.dao.DataIntegrityViolationException

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_SUPER_USER'])
class ClientController {
    static scaffold = true
    def index() { redirect(action:list) }
}
