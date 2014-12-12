angular.module('shamanicApp')

.directive("equals", function() {
	return {
		restrict: 'A',
		require: "?ngModel",
		link: function(scope, elem, attrs, ngModel) {
			if (!ngModel) return;
			scope.$watch(attrs.ngModel, function() {
				validate();
				});
				
			attrs.$observe('equals', function(val) {
				validate();
			});
		
			var validate = function() {
				var val1 = ngModel.$viewValue;
				var val2 = attrs.equals;
				
				ngModel.$setValidity('equals', val1 === val2);
			};
		}
	}
});

//better, but I may have to muck around with the usage

/* app.directive('match', [function () {
  return {
    require: 'ngModel',
    link: function (scope, elem, attrs, ctrl) {
      
      scope.$watch('[' + attrs.ngModel + ', ' + attrs.match + ']', function(value){
        ctrl.$setValidity('match', value[0] === value[1] );
      }, true);

    }
  }
}]); */