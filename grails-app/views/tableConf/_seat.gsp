<%
/*******************************************************************************
 Party Planner web application for guest seat assignments and entree choices
	Copyright (C) 2012  Aaron Mondelblatt

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program.  If not, see http://www.gnu.org/licenses/gpl-3.0.txt.
***********************************************************************************/
 %>
<div id="seat_${s?.id}" class="seat" seat="${s?.id}">
	<div class="header">
		<div>${s?.seatNumber}</div>
		<span class="ui-state-error ui-corner-all delete-user icon-only-holder" seat="${s?.id}" title="Delete Seat"><span class="ui-icon ui-icon-circle-minus"></span></span>
	</div>
	<div class="clear"></div>
	<g:if test="${s?.partyGuest?.id}">
		<g:render template="user"  model="[guest:s?.partyGuest]"/>
	</g:if>
</div>