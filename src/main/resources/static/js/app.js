angular.module('app', [ 'ngRoute', 'ngCookies'])
.run(function run( $http, $cookies ){
	 $http.defaults.headers.post['X-CSRFToken'] = $cookies['csrftoken'];
})
.config(function($routeProvider, $httpProvider, $cookiesProvider) {
	

	$routeProvider.when('/', {
		templateUrl : '/home.html',
		controller : 'navigation'
	}).when('/login', {
		templateUrl : '/login.html',
		controller : 'navigation'
	}).when('/upload', {
		templateUrl : '/api/upload_view.html',
		controller : 'fileCtrl'
	}).otherwise('/');

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

}).controller(
		'navigation',

		function($rootScope, $scope, $http, $location, $route) {

			$scope.tab = function(route) {
				return $route.current && route === $route.current.controller;
			};
			
			var authenticate = function(credentials, callback) {
				
				if (credentials) {
					 var data = 'j_username=' + encodeURIComponent(credentials.username) +
	                    '&j_password=' + encodeURIComponent(credentials.password) +
	                    '&remember-me=' + credentials.rememberMe + '&submit=Login';
		           
					 $http.post('api/authentication', data, {
		                    headers: {
		                        'Content-Type': 'application/x-www-form-urlencoded'
		                    }
		             }).success(function (response) {
		            	 	$scope.error=false;
							$rootScope.authenticated = true;
						callback && callback($rootScope.authenticated);
					}).error(function(response) {
						$rootScope.authenticated = false;
						$scope.error=true;
						callback && callback(false);
					});
				}
	                
            }
            
			authenticate();

			$scope.credentials = {};
			$scope.login = function() {
				authenticate($scope.credentials, function(authenticated) {
					if (authenticated) {
						console.log("Login succeeded")
						$location.path("/");
						$scope.error = false;
						$rootScope.authenticated = true;
					} else {
						console.log("Login failed")
						$location.path("/login");
						$scope.error = true;
						$rootScope.authenticated = false;
					}
				})
			};

			$scope.logout = function() {
				
				 $http.post('api/logout').success(function (response) {
					 $rootScope.authenticated = false;
	                    // to get a new csrf token call the api
					 	$location.path("/");
	                }).error(function(data) {
						console.log("Logout failed")
						$rootScope.authenticated = false;
					});
				 
			}

		});
