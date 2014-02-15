
<%@ page import="com.muhlsoftware.wedding.PartyGuest" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'partyGuest.label', default: 'PartyGuest')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-partyGuest" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<g:render template="../layouts/navMenu" model="[type:'list']" />
		<div id="list-partyGuest" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" />
			
			
			</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="partyGuest.party.label" default="Party" /></th>
					
						<th><g:message code="partyGuest.guest.label" default="Guest" /></th>
					
						<th><g:message code="partyGuest.entree.label" default="Entree" /></th>
					
						<g:sortableColumn property="gift" title="${message(code: 'partyGuest.gift.label', default: 'Gift')}" />
					
						<g:sortableColumn property="thankYouCardSent" title="${message(code: 'partyGuest.thankYouCardSent.label', default: 'Thank You Card Sent')}" />
					
						<g:sortableColumn property="isAttending" title="${message(code: 'partyGuest.isAttending.label', default: 'Is Attending')}" />
					
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${partyGuestInstanceList}" status="i" var="partyGuestInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${partyGuestInstance.id}">${fieldValue(bean: partyGuestInstance, field: "party") ?: "edit"}</g:link></td>
					
						<td>${fieldValue(bean: partyGuestInstance, field: "guest")}</td>
					
						<td>${fieldValue(bean: partyGuestInstance, field: "entree")}</td>
					
						<td>${fieldValue(bean: partyGuestInstance, field: "gift")}</td>
					
						<td><g:formatBoolean boolean="${partyGuestInstance.thankYouCardSent}" /></td>
					
						<td><g:formatBoolean boolean="${partyGuestInstance.isAttending}" /></td>
					
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${partyGuestInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
