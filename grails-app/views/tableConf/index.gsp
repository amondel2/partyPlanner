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
		<link  rel="stylesheet" href="${resource(dir: 'css', file: 'tableConf.css')}" type="text/css">
		<meta name="layout" content="main"/>
		<g:javascript src="tableConf.js"/>
		<title>Plan Your Party</title>
	</head>
<body>
	<g:render template="../layouts/navMenu" />
	<div id="guestListFiliters">
		<ul id="partyPlanMenu">
			<li><a href="#">Action Buttons</a>
				<ul>
					<li>
						<a href="#" id="addTable">Add a Table</a>
					</li>
					<li>
						<a href="#" id="addGuest">Add a Guest</a>
					</li>
					<li>
						<a href="#"  id="fiterAttend" attrib="off">Show Only Attending Guest</a>
					</li>
					<li>
						<a href="#"  id="fiterResponded" attrib="off">Show Guest Who Have Not Responded</a>
					</li>
					<li>
						<a href="#"  id="sortList">Refresh List</a>
					</li>
				</ul>
			</li>
			<li><a href="#">Counts</a>
				<ul>
					<li><a href="#">Invited Guest Count:<span id='invitedCount'></span></a></li>
					<li><a href="#">Attending Guest Count:<span id='attendingCount'></span></a></li>
					<li><a href="#">Unique Address Count:<span id='uniqueCount'></span></a></li>
					<li><a href="#">Guest Count:<span id='guestCount'></span></a></li>
					<g:each in="${entrees}" var="entree">
						<li><a href="#">${entree.entreeName} Count:<span cost='${entree.cost}' id='entree_${entree.id}'>0</span></a></li>
					</g:each>
				</uL>			
			</li>
			<li><a href="#">Money</a>
				<ul>
					<li><a href="#">Total Food Cost: $<span id="totalCost"></span></a></li>
					<li><a href="#">Total Wedding Cost: $<span id='vendorTotalCost'></span></a></li>
					<li><a href="#">Total Amount Paid: $<span id='vendorTotalPaid'></span></a></li>
					<li><a href="#">Total Amount Owed: $<span id='vendorTotalOwed'></span></a></li>
				</uL>			
			</li>
		</ul>
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
	<div id="dialogs">
		<div id="addTableForm" title="Add a Table">
			<input type="text" name="tableName" id="tableName" value="" required="required" placeholder="Enter a Name" />
			<input type="number" min="1" step="1" pattern="\d+" name="seatTotal" id="seatTotal" value="" required="required" placeholder="Enter The Number of Seats" />
		</div>
		<div id="editTableForm" title="Edit a Table">
			<input type="hidden" name="tableId" id="editDialTableId" value="">
			<input type="text" name="tableName" id="editDialTableName" value="" required="required" placeholder="Enter a Name" />
		</div>
		<div id="editGuest" title="Edit a Guest"></div>
		<div id="addGuestDi" title="Add a Guest To The Party"></div>
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