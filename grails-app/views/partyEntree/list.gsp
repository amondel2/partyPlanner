
<%@ page import="com.muhlsoftware.wedding.PartyEntree" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'partyEntree.label', default: 'PartyEntree')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-partyEntree" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<g:render template="../layouts/navMenu" model="[type:'list']" />
		<div id="list-partyEntree" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" />
			
			
			</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="partyEntree.party.label" default="Party" /></th>
					
						<g:sortableColumn property="entreeName" title="${message(code: 'partyEntree.entreeName.label', default: 'Entree Name')}" />
					
						<g:sortableColumn property="cost" title="${message(code: 'partyEntree.cost.label', default: 'Cost')}" />
					
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${partyEntreeInstanceList}" status="i" var="partyEntreeInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${partyEntreeInstance.id}">${fieldValue(bean: partyEntreeInstance, field: "party") ?: "edit"}</g:link></td>
					
						<td>${fieldValue(bean: partyEntreeInstance, field: "entreeName")}</td>
					
						<td>${fieldValue(bean: partyEntreeInstance, field: "cost")}</td>
					
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${partyEntreeInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
