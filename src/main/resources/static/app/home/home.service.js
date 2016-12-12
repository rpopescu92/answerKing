(function() {
    'use strict';

    angular.module('answerKingApp')
            .service('HomeService', HomeService);

     HomeService.$inject = ['$http'];

     function HomeService($http) {

        return {
            getAllItems : getAllItems,
            editPriceItem: editPriceItem
        }

        function getAllItems() {
            return $http({
                url: '/items',
                method: 'GET'
            });
        }

        function editPriceItem(index) {
            return $http({
                url: '/items/'+index,
                method: 'PUT'
            });
        }


     }
})();