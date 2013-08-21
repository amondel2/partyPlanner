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
package com.muhlsoftware.wedding.extralogin.ui

import com.muhlsoftware.wedding.extralogin.ClientAuthentication

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

import org.codehaus.groovy.grails.plugins.springsecurity.RequestHolderAuthenticationFilter
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.util.TextEscapeUtils

class ClientFilter extends RequestHolderAuthenticationFilter {

	@Override
	Authentication attemptAuthentication(HttpServletRequest request,
	                                     HttpServletResponse response)
			throws AuthenticationException {

		if (!request.post) {
			throw new AuthenticationServiceException(
				"Authentication method not supported: $request.method")
		}

		String username = (obtainUsername(request) ?: '').trim()
		String password = obtainPassword(request) ?: ''
		String clientId = request.getParameter('client')

		def authentication = new ClientAuthentication(username, password, clientId)

		HttpSession session = request.getSession(false)
		if (session || getAllowSessionCreation()) {
			request.session[SPRING_SECURITY_LAST_USERNAME_KEY] =
				TextEscapeUtils.escapeEntities(username)
		}

		setDetails request, authentication

		return getAuthenticationManager().authenticate(authentication)
	}
}
