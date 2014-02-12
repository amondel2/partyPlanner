
<%@ page import="com.muhlsoftware.wedding.PartyEntree" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'partyEntree.label', default: 'PartyEntree')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-partyEntree" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		
			<g:render template="../layouts/navMenu" model="[type:'show']" />

				
		<div id="show-partyEntree" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list partyEntree">
			
				<g:if test="${partyEntreeInstance?.party}">
				<li class="fieldcontain">
					<span id="party-label" class="property-label"><g:message code="partyEntree.party.label" default="Party" /></span>
					
						<span class="property-value" aria-labelledby="party-label"><g:link controller="party" action="show" id="${partyEntreeInstance?.party?.id}">${partyEntreeInstance?.party?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${partyEntreeInstance?.entreeName}">
				<li class="fieldcontain">
					<span id="entreeName-label" class="property-label"><g:message code="partyEntree.entreeName.label" default="Entree Name" /></span>
					
						<span class="property-value" aria-labelledby="entreeName-label"><g:fieldValue bean="${partyEntreeInstance}" field="entreeName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${partyEntreeInstance?.cost}">
				<li class="fieldcontain">
					<span id="cost-label" class="property-label"><g:message code="partyEntree.cost.label" default="Cost" /></span>
					
						<span class="property-value" aria-labelledby="cost-label"><g:fieldValue bean="${partyEntreeInstance}" field="cost"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${partyEntreeInstance?.id}" />
					<g:link class="edit" action="edit" id="${partyEntreeInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
