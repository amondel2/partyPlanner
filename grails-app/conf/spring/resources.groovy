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
import com.muhlsoftware.wedding.extralogin.auth.ClientAuthenticationProvider
import com.muhlsoftware.wedding.extralogin.ui.ClientFilter
import com.muhlsoftware.wedding.logout.CustomLogoutSuccessHandler

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

beans = {

	def conf = SpringSecurityUtils.securityConfig

	// custom authentication
	authenticationProcessingFilter(ClientFilter) {
		authenticationManager = ref('authenticationManager')
		sessionAuthenticationStrategy = ref('sessionAuthenticationStrategy')
		authenticationSuccessHandler = ref('authenticationSuccessHandler')
		authenticationFailureHandler = ref('authenticationFailureHandler')
		rememberMeServices = ref('rememberMeServices')
		authenticationDetailsSource = ref('authenticationDetailsSource')
		filterProcessesUrl = conf.apf.filterProcessesUrl
		usernameParameter = conf.apf.usernameParameter
		passwordParameter = conf.apf.passwordParameter
		continueChainBeforeSuccessfulAuthentication = conf.apf.continueChainBeforeSuccessfulAuthentication
		allowSessionCreation = conf.apf.allowSessionCreation
		postOnly = conf.apf.postOnly
	}

	// custom authentication
	daoAuthenticationProvider(ClientAuthenticationProvider) {
		passwordEncoder = ref('passwordEncoder')
		saltSource = ref('saltSource')
		preAuthenticationChecks = ref('preAuthenticationChecks')
		postAuthenticationChecks = ref('postAuthenticationChecks')
	}

	// custom logout redirect
	logoutSuccessHandler(CustomLogoutSuccessHandler) {
		redirectStrategy = ref('redirectStrategy')
		defaultTargetUrl = conf.logout.afterLogoutUrl
	}
}

