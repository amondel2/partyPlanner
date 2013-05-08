<div class="nav" role="navigation">
			<sec:ifLoggedIn>
				<ul style="display:inline-block;">
					<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				</ul>
			</sec:ifLoggedIn>
			<sec:ifLoggedIn>
				<ul style="display:inline-block;">
					<li><a class="partyTime" href="${createLink(uri: '/tableConf/')}"><g:message code="default.partyPlan.label"/></a></li>
				</ul>
			</sec:ifLoggedIn>
			<sec:ifNotLoggedIn>
				<g:link controller='login' action='auth'>Login</g:link>
			</sec:ifNotLoggedIn>
			<sec:ifAllGranted roles="ROLE_ADMIN">
				<ul style="display:inline-block;">
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
					<li><a class="admin" href="${createLink(uri: '/user/')}"><g:message code="default.admin.label"/></a></li>
					<li><a class="guests" href="${createLink(uri: '/importGuest/')}">Import</a></li>
					<li><a class="guests" href="${createLink(uri: '/guest/')}">Guest</a></li>
					<li><a class="seat" href="${createLink(uri: '/seat/')}">Seats</a></li>
					<li><a class="tables" href="${createLink(uri: '/webTable/')}">Tables</a></li>
				</ul>
			</sec:ifAllGranted>
			<div style="float:right;margin-right:5px;">
			<sec:ifLoggedIn>
				<ul>
					 <li><a href="${createLink(uri: '/logout/')}">Logout</a></li>
				</ul>
			</sec:ifLoggedIn>
			</div>
			<div style="clear:both;"></div>
</div>