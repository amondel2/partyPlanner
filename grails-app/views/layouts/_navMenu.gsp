<ul id="menu">
	<sec:ifLoggedIn>
		<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
		<li><a class="partyTime" href="#"><g:message code="default.partyPlan.label"/></a>
			<ul>
				<wed:showPartyDropDown />
			</ul>
		</li>
	</sec:ifLoggedIn>
	<sec:ifNotLoggedIn>
			<li><g:link controller='login' action='auth'>Login</g:link></li>
	</sec:ifNotLoggedIn>
	<sec:ifAnyGranted roles="ROLE_CLIENT_ADMIN">
			<g:if test="${type=='list'}">
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</g:if>
			<g:if test="${type=='create'}">
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
			</g:if>
			<g:if test="${type=='edit'}">
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</g:if>
			<g:if test="${type=='show'}">
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</g:if>
			<li><a href="#">Admin Drop Downs</a>
				<ul>
					<li><a class="admin" href="${createLink(uri: '/user/')}"><g:message code="default.admin.label"/></a></li>
					<li><a class="guests" href="${createLink(uri: '/importGuest/')}">Import</a></li>
					<li><a class="party" href="${createLink(uri: '/party/')}">Party</a></li>
					<li><a class="partyuser" href="${createLink(uri: '/partyGuest/')}">Guest Parties</a></li>
					<li><a class="guests" href="${createLink(uri: '/guest/')}">Guest</a></li>
					<li><a class="seat" href="${createLink(uri: '/seat/')}">Seats</a></li>
					<li><a class="tables" href="${createLink(uri: '/wedTable/')}">Tables</a></li>	
					<sec:ifAnyGranted roles="ROLE_SUPER_USER">
						<li><a class="guests" href="${createLink(uri: '/client/')}">Clients</a></li>
					</sec:ifAnyGranted>			
				</ul>
			</li>
	</sec:ifAnyGranted>
	<sec:ifLoggedIn>
		 <li><a href="${createLink(uri: '/logout/')}">Logout</a></li>
	</sec:ifLoggedIn>
</ul>