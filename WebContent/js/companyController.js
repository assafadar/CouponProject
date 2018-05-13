couponApp.controller('companyController', function ($scope, $http, $location, $rootScope,$cookieStore) {
    var userID = $cookieStore.id;
    $scope.currentUserID = userID;
    var restURL = './rest/';
    $scope.updateSection = false;
    var userType = $cookieStore.role;
    $scope.role = userType;
    $scope.myCoupons=[];
    var allMyCoupons;
    $scope.coupons=[];
    $scope.getCouponType = true;
    $scope.showCoupons = true;
    $scope.updateSection = false;
    function init (){
        debugger;
        if(userType=='Company'){
                getMyCoupons(userID);
                getEndingCoupons();
                getUser(userID);
        }
        else{
            alert('Access Denied');
            $location.path('/');
        }
    }
        init();
        $scope.newCoupon = function(){
            $location.path('/newCoupon');
        }
        function getMyCoupons(id){
                $http({
                    method: 'GET',
                    url: restURL+'coupons/company/'+userID
                }).success(function (data){
                    if(!isEmpty(data)){
                        debugger;
                        allMyCoupons = data;
                        $scope.coupons = data;
                        console.log($scope.myCoupons);
                    }
                    else{
                        alert('No coupons Listed yet');
                    }
                }).error(function (data){
                    console.log(data);
                    alert('Problem in connection');
                })
    }
    function getEndingCoupons(){
        $http({
            method: 'GET',
            url: restURL+'coupons/amount/'+userID
        }).success(function(data){
            debugger;
            if(!isEmpty(data)){
                $scope.endingCoupons = data;
            }
            else{
                alert('No Coupons listed yet');
            }
        }).error(function(data){
            alert('Connection problems in API');
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
    $scope.editCompanyDetails = function (){
        debugger;
        if($scope.updateSection==false){
            changeView('edit');
        }
        else{
            $scope.updateSection = false;
        }
    }
    function changeView(theme){
        debugger;
        if(theme == 'edit'){
            debugger;
            $scope.updateSection = true;
        }
        else{
            debugger;
            $scope.updateSection = false;
        }
    }
    $scope.deleteCoupon = function(couponID){
        $http({
            method: 'DELETE',
            url: restURL+'coupons/'+couponID
        }).success(function(data){
            alert('Coupon was deleted');
            init();
            // getMyCoupons();
            // getEndingCoupons();
        }).error(function(data){
            alert('Couldnt delete coupon');
        })
    }
    $scope.getCouponsByMaxPrice = function(){
        var maxPrice = $scope.price;
        $http({
            method: 'GET',
            url: restURL+'coupons/maxPrice/'+userID+'/'+maxPrice
        }).success(function(data){
            if(!isEmpty(data)){
                $scope.coupons = data;
            }
            else{
                alert('Amount is to low');
            }
        }).error(function(data){
            console.log(data);
            alert('Connection problem, cant get by max price');
        })
    }
    $scope.filterCoupons = function(){
        var couponType = $scope.type;
        $http({
            method: 'GET',
            url: restURL+'coupons/type/'+userID+'/'+couponType
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
        $cookieStore.couponID = couponID;
        $location.path('/coupon');
    }
    $scope.updateUser = function(){
        $scope.updatedUser.role = userType;
        $scope.updatedUser.id = $cookieStore.id;
        $scope.updatedUser.password = $scope.user.password;
        checkUpdatedData();
        var updatedUserJSON = JSON.stringify($scope.updatedUser);

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
    $scope.updateCoupon = function(couponID){
        $cookieStore.updateOrShow = 'update';
        $cookieStore.couponID = couponID;
        $location.path('/coupon');
    }
    $scope.showMyCoupons = function(){
        $scope.coupons = allMyCoupons;
    }
});