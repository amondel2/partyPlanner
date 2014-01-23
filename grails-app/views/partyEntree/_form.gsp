<%@ page import="com.muhlsoftware.wedding.PartyEntree" %>



<div class="fieldcontain ${hasErrors(bean: partyEntreeInstance, field: 'party', 'error')} required">
	<label for="party">
		<g:message code="partyEntree.party.label" default="Party" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="party" name="party.id" from="${com.muhlsoftware.wedding.Party.list()}" optionKey="id" required="" value="${partyEntreeInstance?.party?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: partyEntreeInstance, field: 'entreeName', 'error')} ">
	<label for="entreeName">
		<g:message code="partyEntree.entreeName.label" default="Entree Name" />
		
	</label>
	<g:textField name="entreeName" value="${partyEntreeInstance?.entreeName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: partyEntreeInstance, field: 'cost', 'error')} ">
	<label for="cost">
		<g:message code="partyEntree.cost.label" default="Cost" />
		
	</label>
	<g:field name="cost" value="${fieldValue(bean: partyEntreeInstance, field: 'cost')}"/>
</div>

