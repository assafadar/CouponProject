couponApp.controller('createCouponController', function($scope, $http, $location, $rootScope, $cookieStore) {
    $scope.message = 'Create Coupon page';

    $scope.couponData = {};
    var userID = $cookieStore.id;
    function dateFormatConvert (dateToConvert){
    	var mydate = new Date(dateToConvert);
    	var month = mydate.getUTCMonth() + 1; //months from 1-12
    	var day = mydate.getUTCDate();
    	var year = mydate.getUTCFullYear();
    	
    	if (day<10) {day="0"+day};
    	if (month<10) {month="0"+month};

    	return newdate = day+"/"+month+"/"+year ;
    	
    }
    
    // process the form
    $scope.processForm = function() {  
        $scope.couponData.companyID = userID; 
        $scope.couponData.startDate=dateFormatConvert($scope.couponData.startDate); //Converting start date to YYYY-MM-DD format
        $scope.couponData.endDate=dateFormatConvert($scope.couponData.endDate); //Converting start date to YYYY-MM-DD format
        
        console.log($scope.couponData);
        
        var couponDataJSON = JSON.stringify($scope.couponData);
        console.log(couponDataJSON);
        
        $http({
            method: 'POST',
            url: 'http://localhost:8090/FinalProjectJHB/rest/coupons',
            data: couponDataJSON,
            headers: {'Content-Type' : 'application/json'}
        }).success(function (data){
            alert('New Coupon created');
            debugger;
            $location.path('/company');
        }).error(function(data){
            debugger;
            console.log(data)
        })
    } 
});