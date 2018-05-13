    couponApp.controller('adminController', function($scope, $http, $location, $rootScope,$cookieStore) {
        var restUserURL = './rest/users';
        var restCouponURL = './rest/coupons';
        $scope.currentUserName = $cookieStore.userName;
        $scope.userID = $cookieStore.id;
        $scope.message = 'Admin page';
        $scope.users=[];
        $scope.coupons =[];
        $scope.getIdForManipulation = false;
        $scope.showUsers = false;
        $scope.showCoupon = false;
        $scope.showCoupons = false;
        $scope.updatingUser = false;
        $scope.showUser = false;
        $scope.getFilter = false;
        $scope.getUserType = false;
        $scope.getCouponType = false;
        var selectedFilter;

        function init(){
            var userType = $cookieStore.role;
            if(userType != 'Admin'){
                alert('Access denied');
                $location.path('/');
            }
        }
        init();
        //Filter related functions
       $scope.selectFilter = function(){
           selectedFilter = $scope.filter;
           if(selectedFilter == 1){
               $scope.getFilter = false;
               getAllUsers();
               $scope.showUsers = true;
               $scope.showUser = false;
               $scope.showCoupons = false;
               $scope.updatingUser = false;
           }
           else if(selectedFilter == 2){
            debugger;   
            $scope.getFilter = true;
               changeFilter(1);
               debugger;
               hideAllCouponsInfo();
               hideAllUsersInfo();
           }
           else if(selectedFilter == 3){
                $scope.getFilter = true;
                changeFilter(2);
                hideAllCouponsInfo();
                hideAllUsersInfo();
           }
           else if(selectedFilter == 4){
               $scope.getFilter = false;
               getActivatedUsers();
               hideAllCouponsInfo();
               $scope.showUsers = true;
               $scope.showUser = false;
               $scope.showCoupons = false;
               $scope.updatingUser = false;
           }
           else if(selectedFilter == 5){
               $scope.getFilter = true;
               changeFilter(1); 
                hideAllCouponsInfo();
                hideAllUsersInfo();
           }
           else if(selectedFilter == 6){
               $scope.getFilter = false;
               getAllCoupons();
               hideAllUsersInfo();
               $scope.getCoupon = false;
           }
           else if (selectedFilter == 7){
               $scope.getFilter = true;
               changeFilter(1);
               hideAllCouponsInfo();
               hideAllUsersInfo();
           }
           else if(selectedFilter == 8){
               $scope.getFilter = true;
               changeFilter(3);
               hideAllCouponsInfo();
               hideAllUsersInfo();
            }
           else if(selectedFilter == 9){
               $scope.getFilter = true;
               changeFilter(1);
               hideAllCouponsInfo();
               hideAllUsersInfo();
           }
       }
       function changeFilter(filterIndex){
           if (filterIndex == 1){
                debugger;   
            $scope.getIdForManipulation = true;
               $scope.getUserType = false;
               $scope.getCouponType = false;
               debugger;
           }
           else if(filterIndex==2){
            $scope.getIdForManipulation = false;
            $scope.getUserType = true;
            $scope.getCouponType = false;
           }
           else if(filterIndex == 3){
            $scope.getIdForManipulation = false;
            $scope.getUserType = false;
            $scope.getCouponType = true;
           }
       }
       function hideAllCouponsInfo(){
           $scope.showCoupon = false;
           $scope.showCoupons = false;
       }
       function hideAllUsersInfo (){
           $scope.showUser = false;
           $scope.showUsers = false;
           $scope.updatingUser = false;
       }
       $scope.doFilterWithID = function (){
           if($scope.filter == 2){
               getUser($scope.getFilterData.id);
               debugger; 
               $scope.showUser = true;
            }
           else if($scope.filter == 5){
               updateThisUser($scope.getFilterData.id);
               debugger; 
               $scope.updatingUser = true;
            }
           else if($scope.filter == 7){
            getCoupon($scope.getFilterData.id);
            debugger;
            $scope.showCoupon = true;
           }
           else if($scope.filter == 9){
            debugger;
            updateThisCoupon($scope.getFilterData.id);
           }
       }
       $scope.doFilterWithString = function(){
        debugger;   
        if($scope.filter == 3){
               getUsersByType($scope.type);
           }
           else if($scope.filter == 8){
               getCouponsByType($scope.type);
                debugger;
            }
       }
        // Coupons related functions
        $scope.getCouponDetails = function (couponID){
            $cookieStore.updateOrShow = 'show';
            $cookieStore.couponID = couponID;
            $location.path('/coupon');
        }
        function getAllCoupons(){
            
            $http({
                method: 'GET',
                url: restCouponURL
            }).success(function(data){
                if(!isEmpty(data)){
                    debugger;
                    $scope.coupons = data;
                    $scope.showCoupons = true;
                }
                else{
                    alert('No Coupons');
                }
            }).error(function (data){
                console.log(data);
                alert('Problems getting coupons');
            })
        }
        
        $scope.removeCoupon = function (couponID){
            $http({
                method: 'DELETE',
                url: restCouponURL+'/'+couponID
            }).success(function(data){
                console.log(data);
                alert('Coupon was deleted');
            }).errpr(function (data){
                console.log(data);
                alert('Problem deleting coupon');
            })
        }
        $scope.updateCoupon = function(couponID){
            updateThisCoupon(couponID);
        }
        function updateThisCoupon (couponID){
                $cookieStore.couponID = $scope.getFilterData.id;
                $cookieStore.updateOrShow = 'update';
                $location.path('/coupon');    
        }
        function getCoupon(couponID){
            $cookieStore.updateOrShow = 'show';
            $cookieStore.couponID = couponID;
            $location.path('/coupon');
        }
        function getAllCouponsByCompany(companyID){
            $http({
                method: 'GET',
                url: restCouponURL+'/'+companyID
            }).success(function (data){
                if(!isEmpty(data)){
                    $scope.coupons=data;
                }
                else {
                    alert('No coupons for select company');
                }
            }).error(function (data){
                console.log(data);
                alert('Problem getting coupons for specific company');
            })
        }
        function getAllCouponsByCustomer(selectedUserID){
            $http({
                method: 'GET',
                url: restCouponURL+'/'+selectedUserID 
            }).success(function(data){
                if(!isEmpty(data)){
                    $scope.coupons = data;
                }
                else{
                    alert('No coupons for customer id: '+selectedUserID);
                }
            }).error(function (data){
                alert('Couldnt get all coupons for customer: '+selectedUserID);
            })
        }
        function getCouponsByType (type){
            debugger
            $http({
                method: 'GET',
                url: restCouponURL+'/type/'+type
            }).success(function (data){
                if(!isEmpty(data)){
                    $scope.coupons=data;
                    $scope.showCoupons = true;
                    debugger;
                }
                else{
                    alert('No coupons for type:' +type);
                }
            }).error(function (data){
                alert('Couldnt get coupons for type: '+type);
            })
        }
        function getCouponsByTypeCompanyAndUpToPrice(companyID ,maxPrice){
            var companyID = $scope.companyID;
            var maxPrice = $scope.price;
            $http({
                method: 'GET',
                url: restCouponURL+'/maxPrice/'+companyID+'/'+maxPrice
            }).success(function (data){
                if(!isEmpty(data)){
                    $scope.coupons = data;
                }
                else{
                    alert('No Coupons match the filter');
                }
            }).error(function(data){
                console.log(data);
                alert('Coulndt complete task');
            })
        }
        function getCouponsByComapnyAndType(companyID , type){
            $http({
                method: 'GET',
                url: restCouponURL+'/type/'+companyID+'/'+type
            }).success(function(data){
                if(!isEmpty(data)){
                    $scope.coupons = data;
                }
                else{
                    alert('No coupons for the requested company and type');
                }
            }).error(function (data){
                alert('Couldnt complete task');
                console.log(data);
            })
        }
        function getCouponsByComapnyAndUpToDate(companyID , maxDate){
            var companyID = $scope.companyID;
            var endDate = $scope.data;

            $http({
                method:'GET',
                url: restCouponURL+'/data/'+companyID+'/'+endDate
            }).success(function (data){
                if(!isEmpty(data)){
                    $scope.coupons=data;
                }
                else{
                    alert('No coupons for this company and up to this date');
                }
            }).error(function(data){
                alert('Couldnt complete task');
            })
        }
        //Users related functions
       function getAllUsers(){
            $scope.showUsers = true;
            $scope.showCoupons = false;
            $http({
                method: 'GET',
                url: restUserURL
            }).success(function(data){
                $scope.users = data;
                console.log($scope.users);
            }).error(function (data){
                console.log(data);
                $scope.showUsers = false;
            })  
        }
        $scope.deleteUser = function (index){
            if(!isEmpty($scope.users)){
                var selectUserIDForDeletion = $scope.users[index].id;
            }
            else{
                var selectUserIDForDeletion = index;
            }
            console.log(index);
            $http({
                method: 'DELETE',
                url: restUserURL+'/'+selectUserIDForDeletion
            }).success(function(data){
                alert("Deleted");
            }).error(function(data){
                console.log(data);
                alert('PBs');
            })       }
        function getUsersByType(type){
            debugger;
            console.log("getting users by type: "+type);
            $http({
                method: 'GET',
                url: restUserURL+'/type/'+type
            }).success(function(data){
                $scope.users = data;
                console.log($scope.users);
                $scope.showUsers = true;
            }).error(function(data){
                alert('Problem getting data');
            })
        }
        function updateThisUser(userID){
            getUser(userID);
            debugger;
            $scope.selectedUserID = $scope.user.id;
            $scope.selectedUserName = $scope.user.name;
            $scope.selectedUserUserName = $scope.user.userName;
            $scope.selectedUserEmail = $scope.user.email;
            $scope.selectedUserRole = $scope.user.role;
            $scope.updatingUser = true;
        }
         $scope.updateThisUser=function(userID){
            debugger;
                getUser(userID);
                $scope.selectedUserID = $scope.user.id;
                $scope.selectedUserName = $scope.user.name;
                $scope.selectedUserUserName = $scope.user.userName;
                $scope.selectedUserEmail = $scope.user.email;
                $scope.selectedUserRole = $scope.user.role;
                $scope.showUsers = false;
                $scope.updatingUser = true;
            debugger;
        }
        // function updateThisUser(index){
        //     debugger;
        //     $scope.showUsers = false;
        //     $scope.showCoupons = false;
        //     if(!isEmpty($scope.users)){
        //         $scope.currentUserID = $scope.users[index].id;
        //         $scope.currentUserName = $scope.users[index].name;
        //         $scope.currentUserUserName = $scope.users[index].userName;
        //         $scope.currentUserEmail = $scope.users[index].email;
        //         $scope.currentUserRole = $scope.users[index].role;
        //     }
        //     else {
        //         debugger;
        //         if(getUser(index)){
        //             $scope.currentUserID = $scope.user.id;
        //             $scope.currentUserName = $scope.user.name;
        //             $scope.currentUserUserName = $scope.user.userName;
        //             $scope.currentUserEmail = $scope.user.email;
        //             $scope.currentUserRole = $scope.user.role;
        //             $scope.updatingUser = true;
        //         }
        //         else{
        //             alert('PBs');
        //         }
                
        //     }
        // }
        $scope.updateUser = function(){
            $scope.updatedUser.id= $scope.user.id;
            $scope.updatedUser.role = $scope.user.role;
            $scope.updatedUser.password = $scope.user.password;
            checkUpdatedData();
            var updatedUserJSON = JSON.stringify($scope.updatedUser);
            console.log(updatedUserJSON);
            $http({
                method: 'PUT',
                url: restUserURL,
                data: updatedUserJSON,
                headers: { 'Content-Type': 'application/json' }
            }).success(function (data){
                $location.path('/');
            }).error(function (data){
                alert("Problem updating user, Please try again");
            })
        }
        function checkUpdatedData(){
            if($scope.updatedUser.name == null){
                $scope.updatedUser.name = $scope.user.name;
            }
            if($scope.updatedUser.userName == null){
                $scope.updatedUser.userName = $scope.user.userName;
            }
            if($scope.updatedUser.email == null){
                $scope.updatedUser.email = $scope.user.email;
            }
        }
        function getUser(requestedID){
            debugger;
            $http({
                method: 'GET',
                url: restUserURL+'/'+requestedID
            }).success(function (data){
                debugger;
                $scope.user=data;
            }).error(function(data){
                console.log(data);
                alert('Pbs');
            })
        }
        function getActivatedUsers(){
            $http({
                method: 'GET',
                url: restUserURL+'/activated'
            }).success(function(data){
                if(!isEmpty(data)){
                    $scope.showUsers = true;
                    $scope.users = data;
                }
                else{
                    $scope.showUsers = false;
                    alert('No activated users');
                }
            }).error(function (data){
                console.log(data);
                alert('Problem');
                
            })
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

    