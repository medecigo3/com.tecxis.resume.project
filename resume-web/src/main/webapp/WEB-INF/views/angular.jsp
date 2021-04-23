<html>
<head>
	<title>Hello AngularJS</title>
	<meta charset="UTF-8">

    <!-- Bootstrap core CSS -->
    <link href="resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom fonts for this template -->
    <link href="https://fonts.googleapis.com/css?family=Saira+Extra+Condensed:100,200,300,400,500,600,700,800,900" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i,800,800i" rel="stylesheet">
    <link href="resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <link href="resources/vendor/devicons/css/devicons.min.css" rel="stylesheet">
    <link href="resources/vendor/simple-line-icons/css/simple-line-icons.css" rel="stylesheet">


    <!-- Custom styles for this template -->
    <link href="resources/css/resume.min.css" rel="stylesheet">
    
	<!-- Angular core JavaScript -->
	<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.23/angular.min.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.23/angular-route.js"></script>
    
    <!-- Bootstrap core JavaScript -->
    <script src="resources/vendor/jquery/jquery.min.js"></script>
    <script src="resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Plugin JavaScript -->
    <script src="resources/vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for this template -->
	<script src="resources/js/angular-training/app.js"></script>
	<script src="resources/js/angular-training/controllers.js"></script>
	<script src="resources/js/angular-training/services.js"></script>

</head>
<body ng-app>
	<p>
		Enter Name: <input type="text" ng-model="myname">
	</p>
	<p>Hello {{myname}}!!</p>
</body>
</html>