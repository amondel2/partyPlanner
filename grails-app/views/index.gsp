<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Welcome to Party Planner</title>
	</head>
	<body>
		<g:render template="/layouts/navMenu" />
		<div id="page-body" role="main">
			<h1>Welcome to Party Planner</h1>
			<sec:ifNotLoggedIn>
				<p>Please Login </p>
			</sec:ifNotLoggedIn>
			<sec:ifLoggedIn>
				Welcome Back! 
			</sec:ifLoggedIn>
		</div>
	</body>
</html>
