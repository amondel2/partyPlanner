<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="Grails"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery-ui.css')}" type="text/css">
		<g:javascript src="jquery.js" />
		<g:javascript src="jquery-ui.js"/>
		<g:layoutHead/>
		<r:layoutResources />
		<style type='text/css' media='screen'>
			.nav a.admin {
   				background-image: url('${fam.icon(name: 'user_suit')}');
			}
			.nav a.guests {
				background-image: url('${fam.icon(name: 'group')}');
			}
			.nav a.partyTime {
				background-image: url('${fam.icon(name: 'music')}');
			}
		</style>
	</head>
	<body>
		<g:layoutBody/>
		<div class="footer" role="contentinfo">Copyright © 2012-<g:formatDate format="yyyy"/> Muhlsoftware, LLC</div>
		<r:layoutResources />
	</body>
</html>
