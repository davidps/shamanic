//var app = angular.module("shamanicApp", []);
	
var SubmitCtrl = function($scope) {
	$scope.formLink = function() {
		$scope.link = 'form';
	};
	
	$scope.mapLink = function() {
		$scope.map = 'map';
	};
	
};