<%@ page import="com.muhlsoftware.wedding.Guest"%>



<div
	class="fieldcontain ${hasErrors(bean: guestInstance, field: 'firstName', 'error')} required">
	<label for="firstName"> <g:message code="guest.firstName.label"
			default="First Name" /> <span class="required-indicator">*</span>
	</label>
	<g:textField name="firstName" required=""
		value="${guestInstance?.firstName}" />
</div>

<div
	class="fieldcontain ${hasErrors(bean: guestInstance, field: 'middleName', 'error')} ">
	<label for="middleName"> <g:message
			code="guest.middleName.label" default="Middle Name" />

	</label>
	<g:textField name="middleName" value="${guestInstance?.middleName}" />
</div>

<div
	class="fieldcontain ${hasErrors(bean: guestInstance, field: 'lastName', 'error')} required">
	<label for="lastName"> <g:message code="guest.lastName.label"
			default="Last Name" /> <span class="required-indicator">*</span>
	</label>
	<g:textField name="lastName" required=""
		value="${guestInstance?.lastName}" />
</div>

<div
	class="fieldcontain ${hasErrors(bean: guestInstance, field: 'address1', 'error')} ">
	<label for="address1"> <g:message code="guest.address1.label"
			default="Address1" />

	</label>
	<g:textField name="address1" value="${guestInstance?.address1}" />
</div>

<div
	class="fieldcontain ${hasErrors(bean: guestInstance, field: 'address2', 'error')} ">
	<label for="address2"> <g:message code="guest.address2.label"
			default="Address2" />

	</label>
	<g:textField name="address2" value="${guestInstance?.address2}" />
</div>

<div
	class="fieldcontain ${hasErrors(bean: guestInstance, field: 'city', 'error')} ">
	<label for="city"> <g:message code="guest.city.label"
			default="City" />

	</label>
	<g:textField name="city" value="${guestInstance?.city}" />
</div>

<div
	class="fieldcontain ${hasErrors(bean: guestInstance, field: 'state', 'error')} ">
	<label for="state"> <g:message code="guest.state.label"
			default="State" />

	</label>
	<g:textField name="state" value="${guestInstance?.state}" />
</div>

<div
	class="fieldcontain ${hasErrors(bean: guestInstance, field: 'zip', 'error')} ">
	<label for="zip"> <g:message code="guest.zip.label"
			default="Zip" />

	</label>
	<g:textField name="zip" value="${guestInstance?.zip}" />
</div>

<div
	class="fieldcontain ${hasErrors(bean: guestInstance, field: 'phone', 'error')} ">
	<label for="phone"> <g:message code="guest.phone.label"
			default="Phone" />

	</label>
	<g:textField name="phone" value="${guestInstance?.phone}" />
</div>

<div
	class="fieldcontain ${hasErrors(bean: guestInstance, field: 'guestEmail', 'error')} ">
	<label for="guestEmail"> <g:message
			code="guest.guestEmail.label" default="Guest Email" />

	</label>
	<g:textField name="guestEmail" value="${guestInstance?.guestEmail}" />
</div>

<div
	class="fieldcontain ${hasErrors(bean: guestInstance, field: 'isGuest', 'error')} ">
	<label for="isGuest"> <g:message code="guest.isGuest.label"
			default="Is Guest" />

	</label>
	<g:checkBox name="isGuest" value="${guestInstance?.isGuest}" />
</div>

<div class="fieldcontain ${hasErrors(bean: guestInstance, field: 'client', 'error')} required">
	<input type="hidden" id="client" name="client.id" value="${auth.getClientId()}" />
</div>

<div
	class="fieldcontain ${hasErrors(bean: guestInstance, field: 'partyGuests', 'error')} ">
	<label for="partyGuests"> <g:message
			code="guest.partyGuests.label" default="Party Guests" />
	</label>
	<ul class="one-to-many">
		<g:each in="${guestInstance?.partyGuests?}" var="p">
			<li><g:link controller="partyGuest" action="show" id="${p.id}">
					${p?.encodeAsHTML()}
				</g:link></li>
		</g:each>
		<li class="add"><g:link controller="partyGuest" action="create"
				params="['guest.id': guestInstance?.id]">
				${message(code: 'default.add.label', args: [message(code: 'partyGuest.label', default: 'PartyGuest')])}
			</g:link></li>
	</ul>

</div>

