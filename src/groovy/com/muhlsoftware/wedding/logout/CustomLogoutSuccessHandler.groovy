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
package com.muhlsoftware.wedding.logout

import com.muhlsoftware.wedding.extralogin.ClientAuthentication

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler

class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

	private static final ThreadLocal<Authentication> AUTH_HOLDER = new ThreadLocal<Authentication>()

	void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		AUTH_HOLDER.set authentication
		try {
			super.handle(request, response, authentication)
		}
		finally {
			AUTH_HOLDER.remove()
		}
	}

	@Override
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = AUTH_HOLDER.get()

		String url = super.determineTargetUrl(request, response)

//		if (auth instanceof ClientAuthentication) {
//			ClientAuthentication authentication = auth
//			if (authentication.ClientId == 'Org1') {
//				url = 'http://yahoo.com'
//			}
//			else if (authentication.ClientId == 'Org2') {
//				url = 'http://google.com'
//			}
//		}

		url
	}
}
