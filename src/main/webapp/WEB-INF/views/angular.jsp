<html>
<head>
<title>Hello AngularJS</title>
<meta charset="UTF-8">
<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.23/angular.min.js"></script>
</head>
<body ng-app>
	<p>
		Enter Name: <input type="text" ng-model="myname">
	</p>
	<p>Hello {{myname}}!!</p>
</body>
</html>