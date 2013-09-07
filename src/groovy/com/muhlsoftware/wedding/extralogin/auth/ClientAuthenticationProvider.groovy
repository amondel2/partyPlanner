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
package com.muhlsoftware.wedding.extralogin.auth

import com.muhlsoftware.wedding.User
import com.muhlsoftware.wedding.Client
import com.muhlsoftware.wedding.extralogin.ClientAuthentication

import org.codehaus.groovy.grails.plugins.springsecurity.GormUserDetailsService
import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUser
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.dao.SaltSource
import org.springframework.security.authentication.encoding.PasswordEncoder
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.GrantedAuthorityImpl
import org.springframework.security.core.userdetails.UserDetailsChecker
import org.springframework.security.core.userdetails.UsernameNotFoundException

class ClientAuthenticationProvider implements AuthenticationProvider {

	protected final Logger log = LoggerFactory.getLogger(getClass())

	PasswordEncoder passwordEncoder
	SaltSource saltSource
	UserDetailsChecker preAuthenticationChecks
	UserDetailsChecker postAuthenticationChecks

	Authentication authenticate(Authentication auth) throws AuthenticationException {
		ClientAuthentication authentication = auth

		String password = authentication.credentials
		String username = authentication.name
		String ClientId = authentication.ClientId

		GrailsUser userDetails
		def authorities
		
		def client = Client.findByIdAndActive(ClientId,true)
		
		// use withTransaction to avoid lazy loading exceptions
		User.withTransaction { status ->
			User user = User.findByUsernameAndClient(username,client)
			if (!user) {
				// TODO customize 'springSecurity.errors.login.fail' i18n message in app's messages.properties with org name
				log.warn "User not found: $username in Client $ClientId"
				throw new UsernameNotFoundException('User not found', username)
			}

			authorities = user.authorities.collect { new GrantedAuthorityImpl(it.authority) }
			authorities = authorities ?: GormUserDetailsService.NO_ROLES

			userDetails = new GrailsUser(user.username, user.password,
				user.enabled, !user.accountExpired, !user.passwordExpired,
				!user.accountLocked, authorities, user.id)
		}

		preAuthenticationChecks.check userDetails
		additionalAuthenticationChecks userDetails, authentication
		postAuthenticationChecks.check userDetails

		def result = new ClientAuthentication(userDetails,
           	     authentication.credentials, ClientId, authorities)
		result.details = authentication.details
		result
	}

	protected void additionalAuthenticationChecks(GrailsUser userDetails,
			  ClientAuthentication authentication) throws AuthenticationException {

		def salt = saltSource.getSalt(userDetails)

		if (authentication.credentials == null) {
			log.debug 'Authentication failed: no credentials provided'
			throw new BadCredentialsException('Bad credentials', userDetails)
		}

		String presentedPassword = authentication.credentials
		if (!passwordEncoder.isPasswordValid(userDetails.password, presentedPassword, salt)) {
			log.debug 'Authentication failed: password does not match stored value'

			throw new BadCredentialsException('Bad credentials', userDetails)
		}
	}

	boolean supports(Class<? extends Object> authenticationClass) {
      ClientAuthentication.isAssignableFrom authenticationClass
	}
}
