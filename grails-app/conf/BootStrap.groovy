import com.muhlsoftware.wedding.Role
import com.muhlsoftware.wedding.User
import com.muhlsoftware.wedding.UserRole
import com.muhlsoftware.wedding.Client

class BootStrap {	
    def init = { servletContext ->
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
		def initPassword =  'changeme123' //Please change this to something else
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