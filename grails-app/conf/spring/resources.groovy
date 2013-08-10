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

