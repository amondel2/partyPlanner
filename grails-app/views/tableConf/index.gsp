<!DOCTYPE html>
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
<html>
	<head>
		<meta name="layout" content="main"/>
		<g:javascript src="tableConf.js"/>
		<link  rel="stylesheet" href="${resource(dir: 'css', file: 'tableConf.css')}" type="text/css">
		<title>Plan Your Party</title>
	</head>
<body>
	<g:render template="../layouts/navMenu" />
	<div id="guestListFiliters">
		<input type="checkbox" id="fiterAttend" value="on"><label for="fiterAttend">&nbsp;Show Only Attending Guest</label>
		<span id="sortList" class="ui-state-default ui-corner-all" title="Sort List"><span class="ui-icon ui-icon-arrowrefresh-1-e"></span><span>Refresh List</span></span>
		<span>Invited Guest Count:<span id='invitedCount'></span></span>
		<span>Attending Guest Count:<span id='attendingCount'></span></span>
	</div>
	<div class="guestTableLayout" id="guestTableConfContainer">
		<div id="guestList" >
			<g:render template="guestList" model="[guests:guests]"  />
		</div>
		<div id="tableContainer">
			<g:each in="${tables}" var='t'>
				<g:render template="table" model="[t:t]"  />
			</g:each>
		</div>
	</div>
	<div class="clear"></div>
	<fieldset class="fieldSetButtons">
		<legend>Action Buttons</legend>
		<button icon="" id="addTable">Add a Table</button>
		<button icon="" id="addGuest">Add a Guest</button>
	</fieldset>
	
	<div id="dialogs">
		<div id="addTableForm" title="Add a Table">
			<input type="text" name="tableName" id="tableName" value="" required="required" placeholder="Enter a Name" />
		</div>
		<div id="editGuest" title="Edit a Guest"></div>
		<div id="tableDrop" title="Delete Confirm">
			Are you sure you want to Delete This Table?
		</div>
		<div id="guestDrop" title="Delete Confirm">
			Are you sure you want to Delete This Guest?
		</div>
		<div id="seatDrop" title="Delete Confirm">
			Are you sure you want to Delete This Seat?
		</div>
	</div>
	
	<script>
		var baseDir = '${resource(dir: '', file: '')}';
	</script>
</body>
</html>