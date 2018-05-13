couponApp.controller('customerController', function ($scope, $http, $location, $rootScope,$cookieStore) {
    var userID = $cookieStore.id;
    $scope.currentUserID = userID;
    var restURL = './rest/';
    $scope.updateSection = false;
    var userType = $cookieStore.role;
    var allCoupons;
    $scope.role = userType;
    $scope.myCoupons=[];
    $scope.coupons=[];
    $scope.getCouponType = true;
    $scope.showCoupons = true;
    function init (){
        if(userType == 'Customer'){
            getUser(userID);
        }
        
        else{
            alert('Not allowd');
            $location.path('/');
        }
            debugger;
        getMyCoupons(userID);
            getAllCoupons();
    }
    init();
    function getAllCoupons (){
        $http({
            method:'GET',
            url: restURL+'coupons'
        }).success(function(data){
            if(!isEmpty(data)){
                $scope.coupons =data;
                allCoupons = data;
            }
            else{
                alert('No coupons');
            }
        }).error(function (data){
            console.log(data);
            alert('Problem getting all coupons');
        })
    }
    function getMyCoupons(id){
        debugger;
        $http({
            method: 'GET',
            url: restURL+'coupons/customer/'+userID
        }).success(function (data){
            if(!isEmpty(data)){
                $scope.myCoupons = data;
                console.log($scope.myCoupons);
            }
            else{
                alert('No coupons Bought yet');
            }
        }).error(function (data){
            console.log(data);
            alert('Problem in connection');
        })
    }
    function getUser(id){
        $http({
            method: 'GET',
            url: restURL+'users/'+id
        }).success(function (data){
            $scope.user = data;
            $scope.currentUserUserName = $scope.user.userName;
            $scope.currentUserEmail = $scope.user.email;
            $scope.currentUserName = $scope.user.name; 
            $scope.userType = $scope.user.role;
        }).error(function (data){
            console.log(data);
            alert('Couldnt get User');
        })
    }
    $scope.editCustomerDetails = function (){
        debugger;
        if($scope.updateSection==false){
            changeView('edit');
        }
       else{
        $scope.updateSection = false;
       }
    }
    function changeView(theme){
        
        if(theme=='edit'){
            $scope.updateSection = true;
        }
        else{
            $scope.updateSection = false;
        }
        debugger;
    }
    $scope.filterCoupons = function(){
        var couponType = $scope.type;
        $http({
            method: 'GET',
            url: restURL+'coupons/type/'+couponType
        }).success(function(data){
            if(!isEmpty(data)){
                $scope.coupons=data;
            }
            else{
                alert('no coupons for this type');
            }
        }).error(function (data){
            alert('Couldnt get coupons for type');
        })
    }
    $scope.getCouponDetails = function(couponID){
        $cookieStore.updateOrShow = 'show';
        $cookieStore.couponID = couponID;
        $location.path('/coupon');
    }
    $scope.buyCoupon = function(couponID){
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
    $scope.showAllCoupons = function(){
        $scope.coupons = allCoupons;
    }
    $scope.updateUser = function(){
        $scope.updatedUser.role = userType;
        $scope.updatedUser.id = $cookieStore.id;
        $scope.updatedUser.password = $scope.user.password;
        checkUpdatedData();
        var updatedUserJSON = JSON.stringify($scope.updatedUser);
        console.log(updatedUserJSON);
        $http({
            method: 'PUT',
            url: restURL+'users',
            data: updatedUserJSON,
            headers: { 'Content-Type': 'application/json' }
        }).success(function(data){
            alert('Updated details');
            $location.path('/');
        }).error(function(data){
            alert('COuldnt update user details');
            init();
        })
    }
    function checkUpdatedData(){
        if($scope.updatedUser.name == null){
            $scope.updatedUser.name = $scope.currentUserName;
        }
        if($scope.updatedUser.userName == null){
            $scope.updatedUser.userName = $scope.currentUserUserName;
        }
        if($scope.updatedUser.email == null){
            $scope.updatedUser.email = $scope.currentUserEmail;
        }
    }
    function isEmpty(obj){
        for(var prop in obj){
            if(obj.hasOwnProperty(prop)){
                return false;
            }
        }
        return true;
    }
});