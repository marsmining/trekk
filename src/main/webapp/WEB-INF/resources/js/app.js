//
// app.js - trekk app main
//

/* global angular */

(function() {
    "use strict";

    console.log("trekk app");

    var trekk = angular.module("trekk", ["ngRoute", "ngResource"]);

    trekk.config(["$routeProvider", "$locationProvider",
        function($routeProvider, $locationProvider) {

            $routeProvider.
                when("/customer", {
                    templateUrl: "customerList.html",
                    controller: "CustomerList"
                }).
                when("/customer/create", {
                    templateUrl: "customerDetail.html",
                    controller: "CustomerCreate"
                }).
                when("/customer/:id", {
                    templateUrl: "customerDetail.html",
                    controller: "CustomerEdit"
                }).
                otherwise({
                    redirectTo: "/customer"
                });

            $locationProvider.html5Mode(true);
        }]);

    trekk.factory("Customer", ["$resource",
        function($resource) {
            return $resource("api/customer", {}, {});
        }]);

    trekk.controller("CustomerList", ["$scope", "$location", "Customer",
        function($scope, $location, Customer) {
            console.log("CustomerList Controller");

            $scope.customers = Customer.query();

            $scope.edit = function(idx) {
                console.log("edit: ", idx);
                $location.url("/customer/" + idx);
            };

            $scope.remove = function(idx) {
                console.log("remove: ", idx);
            };

            $scope.navigation = function(idx) {
                console.log("navigation: ", idx);
            };

            $scope.create = function() {
                console.log("create");
                $location.url("/customer/create");
            };

        }]);

    trekk.controller("CustomerCreate", ["$scope", "$location", "Customer",
        function($scope, $location, Customer) {
            console.log("CustomerCreate Controller");

            $scope.create = function(c) {
                console.log("create: ", c);
                var nc = new Customer(c);
                nc.$save();
            };

            $scope.home = function() {
                $location.url("/");
            };
        }]);

    trekk.controller("CustomerEdit", ["$scope", "$routeParams",
        function($scope, $routeParams) {
            console.log("CustomerEdit Controller");
            $scope.msg = "kerpow! id: " + $routeParams.id;
        }]);

}());