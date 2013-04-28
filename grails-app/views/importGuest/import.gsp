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
		<title>Import Sheet</title>
	</head>
	<body>
		<div>${msg}</div>
		<form action="upload" method="POST" enctype="multipart/form-data">
		 Choose An Excel File of Guest To Upload <input type="file" name="excel" id="excel">
		 <br>
		 <input type="hidden" value="up" name="up">
		 <input type="submit" value="Submit">
		 
		</form>
	</body>
</html>