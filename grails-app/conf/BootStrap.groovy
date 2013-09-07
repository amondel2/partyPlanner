/*******************************************************************************
 Party Planner web application for guest seat assignments and entree choices
 Copyright (C) 2012  Aaron Mondelblatt
 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.
 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.
 You should have received a copy of the GNU General Public License
 along with this program.  If not, see http://www.gnu.org/licenses/gpl-3.0.txt.
 ***********************************************************************************/
import com.muhlsoftware.wedding.Role
import com.muhlsoftware.wedding.User
import com.muhlsoftware.wedding.UserRole
import com.muhlsoftware.wedding.Client
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.commons.ApplicationAttributes
import org.springframework.web.context.WebApplicationContext

class BootStrap {	
	
	def springSecurityService
	
    def init = { servletContext ->
		
		if(!springSecurityService) {
			//Burt in 08
			def ctx = servletContext.getAttribute(ApplicationAttributes.APPLICATION_CONTEXT)
			springSecurityService =  ctx?.springSecurityService
			if(springSecurityUiService) {
				//Graeme in 06
				WebApplicationContext appCtx = WebApplicationContextUtils.getWebApplicationContext(ctx)
				springSecurityService = appCtx.getBean("springSecurityService")
			}
		}
		//Make sure the following Roles exists if not create them
		def adminRole = Role.findByAuthority('ROLE_SUPER_USER')
		if(!adminRole) {
			adminRole = new Role(authority: 'ROLE_SUPER_USER').save(flush: true)
		}
		def clientAdmin = Role.findByAuthority('ROLE_CLIENT_ADMIN')
		if(!clientAdmin) {
			clientAdmin = new Role(authority: 'ROLE_CLIENT_ADMIN').save(flush: true)
		}
		if(!Role.findByAuthority('ROLE_USER')) {
			new Role(authority: 'ROLE_USER').save(flush: true)
		}
		//create supper client
		def superUserName =  'you'  //Please change this to something else
		def initPassword =  springSecurityService.encodePassword('changeme123') //Please change this to something else
		def supperClient = 'client0' // Please change this to something else
		def normClientName = 'wedding' // Please change this to something else
		def suClient = Client.findByName(supperClient)
		if(!suClient) {
			suClient = new Client(name: supperClient)
			suClient.save(flush: true)
		}
		def normClient = Client.findByName(normClientName)
		if(!normClient) {
			normClient = new Client(name: normClientName)
			normClient.save(flush: true)
		}
		def testUser = User.findByUsername(superUserName)
		if(!testUser) {
			testUser = new User(username: superUserName, enabled: true, password: initPassword,client:suClient)
			testUser.save(flush: true)
		}
		if(!UserRole.findByUserAndRole(testUser,adminRole)) {		
			UserRole.create testUser, adminRole, true
		}
		if(!UserRole.findByUserAndRole(testUser,clientAdmin)) {
			UserRole.create testUser, clientAdmin, true
		}

    }
    def destroy = {
    }
}