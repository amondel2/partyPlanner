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
