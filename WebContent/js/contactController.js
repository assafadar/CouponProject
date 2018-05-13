couponApp.controller('contactController', function($scope,$http,$location) {
    $scope.message="";
    $scope.subject="na";
    $scope.contact = function() {
        alert("Dear "+$scope.name+" , Your Message kept in our system and will be handled as soon as possible");
        var contactRequestJSON = JSON.Stringfy($scope.contactRequest);
        $http({
            method: 'POST',
            url ='./contact',
            data: contactRequestJSON,
            headers: { 'Content-Type': 'application/json' }
        }).success(function(data){
            alert('Request submitted, We will contact you back ASAP');
            $location.path('/');
        })
    };

});