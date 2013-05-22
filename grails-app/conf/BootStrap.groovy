import com.muhlsoftware.wedding.Role
import com.muhlsoftware.wedding.User
import com.muhlsoftware.wedding.UserRole


class BootStrap {	
    def init = { servletContext ->
		//Make sure the following Roles exists if not create them
		def adminRole = Role.findByAuthority('ROLE_ADMIN')
		if(!adminRole) {
			adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true)
		}
		if(!Role.findByAuthority('ROLE_USER')) {
			new Role(authority: 'ROLE_USER').save(flush: true)
		}
		def superUserName =  'you'  //Please change this to something else
		def initPassword =  'changeme123' //Please change this to something else
		def testUser = User.findByUsername(superUserName)
		if(!testUser) {
			testUser = new User(username: superUserName, enabled: true, password: initPassword)
			testUser.save(flush: true)
		}
		if(!UserRole.findByUser(testUser)) {		
			UserRole.create testUser, adminRole, true
		}
    }
    def destroy = {
    }
}