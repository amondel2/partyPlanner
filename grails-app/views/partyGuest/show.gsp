
<%@ page import="com.muhlsoftware.wedding.PartyGuest" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'partyGuest.label', default: 'PartyGuest')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-partyGuest" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		
			<g:render template="../layouts/navMenu" model="[type:'show']" />

				
		<div id="show-partyGuest" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list partyGuest">
			
				<g:if test="${partyGuestInstance?.party}">
				<li class="fieldcontain">
					<span id="party-label" class="property-label"><g:message code="partyGuest.party.label" default="Party" /></span>
					
						<span class="property-value" aria-labelledby="party-label"><g:link controller="party" action="show" id="${partyGuestInstance?.party?.id}">${partyGuestInstance?.party?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${partyGuestInstance?.guest}">
				<li class="fieldcontain">
					<span id="guest-label" class="property-label"><g:message code="partyGuest.guest.label" default="Guest" /></span>
					
						<span class="property-value" aria-labelledby="guest-label"><g:link controller="guest" action="show" id="${partyGuestInstance?.guest?.id}">${partyGuestInstance?.guest?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${partyGuestInstance?.entree}">
				<li class="fieldcontain">
					<span id="entree-label" class="property-label"><g:message code="partyGuest.entree.label" default="Entree" /></span>
					
						<span class="property-value" aria-labelledby="entree-label"><g:link controller="partyEntree" action="show" id="${partyGuestInstance?.entree?.id}">${partyGuestInstance?.entree?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${partyGuestInstance?.gift}">
				<li class="fieldcontain">
					<span id="gift-label" class="property-label"><g:message code="partyGuest.gift.label" default="Gift" /></span>
					
						<span class="property-value" aria-labelledby="gift-label"><g:fieldValue bean="${partyGuestInstance}" field="gift"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${partyGuestInstance?.thankYouCardSent}">
				<li class="fieldcontain">
					<span id="thankYouCardSent-label" class="property-label"><g:message code="partyGuest.thankYouCardSent.label" default="Thank You Card Sent" /></span>
					
						<span class="property-value" aria-labelledby="thankYouCardSent-label"><g:formatBoolean boolean="${partyGuestInstance?.thankYouCardSent}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${partyGuestInstance?.isAttending}">
				<li class="fieldcontain">
					<span id="isAttending-label" class="property-label"><g:message code="partyGuest.isAttending.label" default="Is Attending" /></span>
					
						<span class="property-value" aria-labelledby="isAttending-label"><g:formatBoolean boolean="${partyGuestInstance?.isAttending}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${partyGuestInstance?.seat}">
				<li class="fieldcontain">
					<span id="seat-label" class="property-label"><g:message code="partyGuest.seat.label" default="Seat" /></span>
					
						<span class="property-value" aria-labelledby="seat-label"><g:link controller="seat" action="show" id="${partyGuestInstance?.seat?.id}">${partyGuestInstance?.seat?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${partyGuestInstance?.id}" />
					<g:link class="edit" action="edit" id="${partyGuestInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
