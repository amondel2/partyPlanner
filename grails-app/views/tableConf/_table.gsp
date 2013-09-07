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
<div id="table_${t?.id}" class="table" table="${t?.id}" top="${t?.horzOffset?:''}" left="${t.verOffset ?: ''}">
	<div class="header">
		<div title="${t?.toString()}">${t?.toString()}</div>
		<div>
			<span class="ui-state-default ui-corner-all icon-only-holder edit-table" table="${t?.id}" title="Edit Table"><span class="ui-icon ui-icon-pencil"></span></span>
			
			<span class="ui-state-error ui-corner-all icon-only-holder" title="Delete Table" table="${t?.id}"><span class="ui-icon ui-icon-circle-minus"></span></span>
			<span class="ui-state-default ui-corner-all seatAdder" title="Add Seat" table="${t?.id}"><span class="ui-icon ui-icon-circle-plus"></span><span>Add Seat</span></span>
		</div>
	</div>
	<div class="clear"></div>
	<g:each in="${t?.seats?.sort{a,b -> a.seatNumber < b.seatNumber ? 0 : 1 }}" var='s'>
		<g:render template="seat" model="[s:s]"  />
	</g:each>
</div>