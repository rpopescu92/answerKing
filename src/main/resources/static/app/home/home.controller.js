(function() {
    'use strict';

    angular.module('answerKingApp')
            .controller('HomeController', HomeController);

    HomeController.$inject = ['$state', '$scope', 'HomeService', '$mdDialog', '$rootScope', 'OrderService'];

    function HomeController($state, $scope, HomeService, $mdDialog, $rootScope, OrderService) {
        $scope.items = [];
        $scope.addItem = addItem;
        $scope.editPriceItem = editPriceItem;
        $scope.addToCart = addToCart;
        $scope.checkout = checkout;

        $scope.selections = [];
        $scope.orders = [];
        $scope.errorMessage;
        $scope.order;
        $scope.total;

        init();


        function init() {
            HomeService.getAllItems()
                        .then(function(data) {
                            $scope.items = data.data;
                        },
                         function(error){
                            $scope.errorMessage = "Cannot retrieve all items";
                         }
                        );
        }

        function addItem() {
            $mdDialog.show({
               templateUrl: '/app/item/item.html',
               controller: 'ItemController'
          });
        }

        function editPriceItem(index) {
            HomeService.editPriceItem(index)
                        .then(function(data) {

             });
        }

        function addToCart(item) {
            var index = $scope.selections.indexOf(item);
            if(index != -1) {
                $scope.selections.splice(index,1);
            } else {
                 $scope.selections.push(item);
             }
        }

        function checkout() {
            OrderService.createOrder()
                         .then(function(data) {
                              $scope.order = data.data;
                              $scope.orders.push(data.data);
                              for (var i=0; i<$scope.selections.length; i++) {
                                     OrderService.addItemToOrder($scope.order.id, $scope.selections[i].id)
                                              .then(function(data) {
                                                    $scope.addedToOrder = true;
                                                },
                                                function(error) {
                                                    $scope.addedToOrder = false;
                                                 });
                             }
              },
                function(error){
                $scope.errorMessage = "Cannot create new order";
               });
        }

        $rootScope.$on('refresh-data', function() {
               init();
         });

    }
})();