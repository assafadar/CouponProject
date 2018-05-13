couponApp.controller('loginController', function ($scope, $http, $location, $rootScope,$cookieStore) {
    debugger;
    $scope.message = '';

    $scope.loginData = {};
    $scope.login = function () {
        debugger;
        var loginDataJSON = JSON.stringify($scope.loginData);
        console.log(loginDataJSON);

        $http(
            {
                method: 'POST',
                url: './rest/login',
                data: loginDataJSON,
                headers: { 'Content-Type': 'application/json' }
            }).success(function (data) {
                //                alert("Coupon created successfully"); 
                //                console.log(data);
                
                console.log(data);
                $cookieStore.userName = data.userName;
                $cookieStore.id = data.id;
                $cookieStore.role = data.role;
                console.log($cookieStore)
                debugger;
                if (data.role == "Admin") {
                    
                 $location.path('/admin');
                }
               else if (data.role == "Company") {
                     $location.path('/company');
                }
                else if(data.role == "Customer"){
                    $location.path('/customer');
                }
                else{
                    alert("Invalid!");
                    $location.path('/');
                }

            }).error(function (data) {
                $scope.message = "Invalid Username or Password";
                //                alert("Failed to create coupon"); 
                //                console.log(data);
            });
        console.log("POST done");
    }

    $scope.$watch('loginData', function () {
        $scope.message = '';
        //        console.log('loginData has been changed');
    }, true);

});