couponApp.controller('registerController', function ($scope, $http, $location, $rootScope,$cookieStore) {

    $scope.message = 'Register Now';

    $scope.register = function(){
        debugger;
        var registerDataJSON = JSON.stringify($scope.registerData);
        $http({
            method: 'POST',
            url: './rest/users/register',
            data: registerDataJSON,
            headers: { 'Content-Type': 'application/json' }
        }).success(function(data){
            $cookieStore.userName=data.userName;
            $cookieStore.id = data.id;
            $location.path('/')
        }).error(function (data){
            $scope.message = 'Invalid data, Please try again';
            alert($scope.message);
        })
    }
    
});