<%@ page import="com.muhlsoftware.wedding.Party" %>



<div class="fieldcontain ${hasErrors(bean: partyInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="party.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${partyInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: guestInstance, field: 'client', 'error')} required">
	<input type="hidden" id="client" name="client.id" value="${auth.getClientId()}" />
</div>


<div class="fieldcontain ${hasErrors(bean: partyInstance, field: 'partyGuests', 'error')} ">
	<label for="partyGuests">
		<g:message code="party.partyGuests.label" default="Party Guests" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${partyInstance?.partyGuests?}" var="p">
    <li><g:link controller="partyGuest" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="partyGuest" action="create" params="['party.id': partyInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'partyGuest.label', default: 'PartyGuest')])}</g:link>
</li>
</ul>

</div>

<div class="fieldcontain ${hasErrors(bean: partyInstance, field: 'wedTables', 'error')} ">
	<label for="wedTables">
		<g:message code="party.wedTables.label" default="Wed Tables" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${partyInstance?.wedTables?}" var="w">
    <li><g:link controller="wedTable" action="show" id="${w.id}">${w?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="wedTable" action="create" params="['party.id': partyInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'wedTable.label', default: 'WedTable')])}</g:link>
</li>
</ul>

</div>

