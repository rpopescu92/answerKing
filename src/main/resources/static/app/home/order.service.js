(function() {
    'use strict';

    angular.module('answerKingApp')
            .service('OrderService', OrderService);

    OrderService.$inject = ['$http'];

    function OrderService($http) {

        return {
            createOrder: createOrder,
            addItemToOrder: addItemToOrder,
            pay: pay
        }

        function createOrder() {
            return $http({
                url:'/order',
                method: 'POST'
            });
        }

        function addItemToOrder(orderId, itemId) {
            return $http({
                url: '/order/'+ orderId+"/addItem/" + itemId,
                method: 'PUT'
            });
        }

        function pay(orderId, payment) {
            return $http({
                url: orderId +'/pay',
                data: data,
                method: 'PUT'
            });
        }
    }
})();