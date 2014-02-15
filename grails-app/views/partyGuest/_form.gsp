<%@ page import="com.muhlsoftware.wedding.PartyGuest" %>



<div class="fieldcontain ${hasErrors(bean: partyGuestInstance, field: 'party', 'error')} required">
	<label for="party">
		<g:message code="partyGuest.party.label" default="Party" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="party" name="party.id" from="${com.muhlsoftware.wedding.Party.list()}" optionKey="id" required="" value="${partyGuestInstance?.party?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: partyGuestInstance, field: 'guest', 'error')} required">
	<label for="guest">
		<g:message code="partyGuest.guest.label" default="Guest" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="guest" name="guest.id" from="${com.muhlsoftware.wedding.Guest.list()}" optionKey="id" required="" value="${partyGuestInstance?.guest?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: partyGuestInstance, field: 'entree', 'error')} ">
	<label for="entree">
		<g:message code="partyGuest.entree.label" default="Entree" />
		
	</label>
	<g:if test="${partyGuestInstance?.party}">
		<g:select id="entree" name="entree.id" from="${com.muhlsoftware.wedding.PartyEntree.findAllByParty(partyGuestInstance?.party)}" optionKey="id" value="${partyGuestInstance?.entree?.id}" class="many-to-one" noSelection="['null': '']"/>
	</g:if>
	<g:elseif test="${partyInstance}">
		<g:select id="entree" name="entree.id" from="${com.muhlsoftware.wedding.PartyEntree.findAllByParty(partyInstance)}" optionKey="id" value="${partyGuestInstance?.entree?.id}" class="many-to-one" noSelection="['null': '']"/>
	</g:elseif>
	<g:else>
		<g:select id="entree" name="entree.id" from="${com.muhlsoftware.wedding.PartyEntree.list()}" optionKey="id" value="${partyGuestInstance?.entree?.id}" class="many-to-one" noSelection="['null': '']"/>
	</g:else>
</div>

<div class="fieldcontain ${hasErrors(bean: partyGuestInstance, field: 'gift', 'error')} ">
	<label for="gift">
		<g:message code="partyGuest.gift.label" default="Gift" />
		
	</label>
	<g:textField name="gift" value="${partyGuestInstance?.gift}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: partyGuestInstance, field: 'thankYouCardSent', 'error')} ">
	<label for="thankYouCardSent">
		<g:message code="partyGuest.thankYouCardSent.label" default="Thank You Card Sent" />
		
	</label>
	<g:checkBox name="thankYouCardSent" value="${partyGuestInstance?.thankYouCardSent}" />
</div>

<div class="fieldcontain ${hasErrors(bean: partyGuestInstance, field: 'isAttending', 'error')} ">
	<label for="isAttending">
		<g:message code="partyGuest.isAttending.label" default="Is Attending" />
		
	</label>
	<g:checkBox name="isAttending" value="${partyGuestInstance?.isAttending}" />
</div>

<div class="fieldcontain ${hasErrors(bean: partyGuestInstance, field: 'seat', 'error')} ">
	<label for="seat">
		<g:message code="partyGuest.seat.label" default="Seat" />
		
	</label>
	<g:select id="seat" name="seat.id" from="${com.muhlsoftware.wedding.Seat.list()}" optionKey="id" value="${partyGuestInstance?.seat?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

