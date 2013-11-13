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
                when("/customer/:id/navigation", {
                    templateUrl: "navigationList.html",
                    controller: "NavigationList"
                }).
                otherwise({
                    redirectTo: "/customer"
                });

            $locationProvider.html5Mode(true);
        }]);

    trekk.factory("Customer", ["$resource",
        function($resource) {
            return $resource("/api/customer/:id", {}, {
                query  : { method : "GET", isArray : true },
                post   : { method : "POST" },
                update : { method : "PUT", params: { id: "@id" }},
                remove : { method : "DELETE" }
            });
        }]);

    trekk.factory("Navigation", ["$resource",
        function($resource) {
            return $resource("/api/customer/:id/navigation", {}, {});
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
                Customer.remove({id : idx}, function() {
                    console.log("success");
                    $scope.customers.splice(findIndexById($scope.customers, idx), 1);
                });
            };

            $scope.navigation = function(idx) {
                console.log("navigation: ", idx);
                $location.url("/customer/" + idx + "/navigation");
            };

            $scope.create = function() {
                console.log("create");
                $location.url("/customer/create");
            };

        }]);

    trekk.controller("CustomerCreate", ["$scope", "$location", "Customer",
        function($scope, $location, Customer) {
            console.log("CustomerCreate Controller");
            $scope.action = "Create";

            $scope.customer = {
                gender : "w",
                birthday : "1970-01-01",
                lastContact : "2013-01-01",
                lifetimeValue : 100
            };

            $scope.save = function(c) {
                console.log("save: ", c);
                var nc = new Customer(c);
                nc.$save().then(function() {
                    console.log("success");
                    $scope.isError = false;
                    $location.url("/customer");
                }, function(e) {
                    console.log("error", e);
                    $scope.isError = true;
                    $scope.errorMsg = e.data;
                });
            };

            $scope.home = function() {
                $location.url("/");
            };
        }]);

    trekk.controller("CustomerEdit", ["$scope", "$location", "$routeParams", "Customer",
        function($scope, $location, $routeParams, Customer) {
            console.log("CustomerEdit Controller");
            $scope.action = "Edit";

            $scope.customer = Customer.get({id : $routeParams.id});

            $scope.save = function(c) {
                console.log("save: ", c);
                c.$update().then(function() {
                    console.log("success");
                    $scope.isError = false;
                    $location.url("/customer");
                }, function(e) {
                    console.log("error", e);
                    $scope.isError = true;
                    $scope.errorMsg = e.data;
                });
            };

            $scope.home = function() {
                $location.url("/");
            };
        }]);


    trekk.controller("NavigationList", ["$scope", "$location", "$routeParams", "Navigation",
        function($scope, $location, $routeParams, Navigation) {
            console.log("NavigationList Controller");
            $scope.navigations = Navigation.query({id : $routeParams.id});
            $scope.home = function() {
                $location.url("/");
            };
        }]);

    function findIndexById(xs, n) {
        var i, l = xs.length;
        for (i=0; i < l; i++) {
            if (xs[i].id === n) return i;
        }
        throw new Error("id not found");
    }
}());