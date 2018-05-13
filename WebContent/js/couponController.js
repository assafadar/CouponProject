couponApp.controller('couponController', function ($scope, $http, $location, $rootScope,$cookieStore) {

    var couponID = $cookieStore.couponID;
    var restURL = './rest/';
    function init(){
        debugger;
        if($cookieStore.updateOrShow == 'show'){
            $scope.updateCouponSection = false;
            $scope.showCouponSection = true;
        }
        else if($cookieStore.updateOrShow == 'update'){
            $scope.showCouponSection = false;
            $scope.updateCouponSection = true;
        }
        else{
            alert('Access Denied');
            $location.path('/');
        }
        getCoupon(couponID);
    }
    init();
    
    function getCoupon(couponID){
        debugger;
        $http({
            method: 'GET',
            url: restURL+'coupons/'+couponID
        }).success(function (data){
            $scope.coupon = data;
        }).error(function(data){
            console.log(data);
            alert('Problems getting coupon details');
        })
    }
    $scope.buyCoupon = function(){
        $http({
            method: 'POST',
            url: restURL+'coupons/buy/'+couponID
        }).success(function(data){
            alert('Coupon was added to purchase list');
            getMyCoupons();
        }).error(function (data){
            console.log(data);
            alert('Problem buying this coupons');
        })
    }
    $scope.isCouponAvialable = function(){
        if($scope.coupon.amount>0){
            return false;
        }
        return true;
    }
    $scope.updateCoupon = function(){
        $scop.updatedCouponData.id = $cookieStore.id;
        $scop.updatedCouponData.startDate = $scope.coupon.startDate;
        $scop.updatedCouponData.title = $scope.coupon.title;
        $scop.updatedCouponData.type = $scope.coupon.type;
        $scop.updatedCouponData.message = $scope.coupon.message;
        $scop.updatedCouponData.companyID = $scope.coupon.companyID;
        checkUpdateData();
        var updateCouponJSON = JSON.stringify($scope.updatedCouponData);

        $http({
            method: 'PUT',
            url: restURL+'coupons/',
            data: updatedCouponJSON,
            headers: { 'Content-Type': 'application/json'}
        }).success(function(data){
          alert('coupon was updated');  
          $location.path('/company');  
        }).error(function(data){
            console.log(data);
            alert('Problem updating coupon');
        })
    }
    function checkUpdateData(){
        if($scope.updatedCouponData.endDate == null){
            $scope.updatedCouponData.endDate = $scope.coupon.endDate;
        }
        if($scope.updatedCouponData.amount == null){
            $scope.updatedCouponData.amount = $scope.coupon.amount;
        }
        if($scope.updatedCouponData.price == null){
            $scope.updatedCouponData.price = $scope.coupon.price;
        }
        if($scope.updatedCouponData.img == null){
            $scope.updatedCouponData.img = $scope.coupon.img;
        }

    }
});