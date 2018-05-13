var couponApp = angular.module("couponApp", ["ngRoute","ngCookies"]);

// configure our routes
couponApp.config(function($routeProvider) {
  $routeProvider

    // route for the home page
    .when("/", {
      templateUrl: "templates/login.htm",
      controller: "loginController"
    })

    // route for the register page
    .when("/register", {
      templateUrl: "templates/register.htm",
      controller: "registerController"
    })

    // route for the coupon page 
    .when("/coupon", {
      templateUrl: "templates/coupon.htm",
      controller: "couponController"
    })

    // route for the admin page
    .when("/admin", {
      templateUrl: "templates/admin.htm",
      controller: "adminController"
    })

    // route for the customer page
    .when("/customer", {
      templateUrl: "templates/customer.htm",
      controller: "customerController"
    })

    // route for the company page
    .when("/company", {
      templateUrl: "templates/company.htm",
      controller: "companyController"
    })

    // route for the create coupon page
    .when("/newCoupon", {
      templateUrl: "templates/newCoupon.htm",
      controller: "createCouponController"
    })
    // route for the create company page
    .when("/contact-us", {
      templateUrl: "templates/contactUs.htm",
      controller: "contactController"
    });
});
