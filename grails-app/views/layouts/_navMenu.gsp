<ul id="menu">
	<sec:ifLoggedIn>
		<li><a class="home" style="padding:0px;" href="${createLink(uri: '/')}"><span class="iconSet"></span><span class="navText"><g:message code="default.home.label"/></span></a></li>
		<li><a class="partyTime" href="#"><g:message code="default.partyPlan.label"/></a>
			<ul>
				<wed:showPartyDropDown />
			</ul>
		</li>
	</sec:ifLoggedIn>
	<sec:ifNotLoggedIn>
			<li><a class="login" href="${createLink(uri: '/login/auth')}"><span class="iconSet"></span><span class="navText">Login</span></a></li>
	</sec:ifNotLoggedIn>
	<sec:ifAnyGranted roles="ROLE_CLIENT_ADMIN">
			<g:if test="${type=='list'}">
				<li><g:link class="create" style="padding:0px;" action="create"><span class="iconSet"></span><span class="navText"><g:message code="default.new.label" args="[entityName]" /></span></g:link></li>
			</g:if>
			<g:if test="${type=='create'}">
				<li><g:link class="list" style="padding:0px;" action="list"><span class="iconSet"></span><span class="navText"><g:message code="default.list.label" args="[entityName]" /></span></g:link></li>
			</g:if>
			<g:if test="${type=='edit'}">
				<li><g:link class="list" style="padding:0px;" action="list"><span class="iconSet"></span><span class="navText"><g:message code="default.list.label" args="[entityName]" /></span></g:link></li>
				<li><g:link class="create"  style="padding:0px;" action="create"><span class="iconSet"></span><span class="navText"><g:message code="default.new.label" args="[entityName]" /></span></g:link></li>
			</g:if>
			<g:if test="${type=='show'}">
				<li><g:link class="list" style="padding:0px;" action="list"><span class="iconSet"></span><span class="navText"><g:message code="default.list.label" args="[entityName]" /></span></g:link></li>
				<li><g:link class="create" style="padding:0px;" action="create"><span class="iconSet"></span><span class="navText"><g:message code="default.new.label" args="[entityName]" /></span></g:link></li>
			</g:if>
			<li><a href="#">Admin Drop Downs</a>
				<ul>
					<li><a class="admin" href="${createLink(uri: '/user/')}"><g:message code="default.admin.label"/></a></li>
					<li><a class="guests" href="${createLink(uri: '/importGuest/')}">Import</a></li>
					<li><a class="party" href="${createLink(uri: '/party/')}"><span class="iconSet"></span><span class="navText">Party</span></a></li>
					<li><a class="partyuser" href="${createLink(uri: '/partyGuest/')}"><span class="iconSet"></span><span class="navText">Guest Parties</span></a></li>
					<li><a class="guests" href="${createLink(uri: '/guest/')}">Guest</a></li>
					<li><a class="seat" href="${createLink(uri: '/seat/')}"><span class="iconSet"></span><span class="navText">Seats</span></a></li>
					<li><a class="entree" href="${createLink(uri: '/partyEntree/')}"><span class="iconSet"></span><span class="navText">Party Entree</span></a></li>
					<li><a class="tables" href="${createLink(uri: '/wedTable/')}"><span class="iconSet"></span><span class="navText">Tables</span></a></li>
					<li><a class="partyVendor" href="${createLink(uri: '/partyVendor/')}"><span class="iconSet"></span><span class="navText">Vendors</span></a></li>	
					<sec:ifAnyGranted roles="ROLE_SUPER_USER">
						<li><a class="guests" href="${createLink(uri: '/client/')}">Clients</a></li>
					</sec:ifAnyGranted>			
				</ul>
			</li>
	</sec:ifAnyGranted>
	<sec:ifLoggedIn>
		 <li><a class="logout" style="padding:0px;" href="${createLink(uri: '/logout/')}"><span class="iconSet"></span><span class="navText">Logout</span></a></li>
	</sec:ifLoggedIn>
</ul>