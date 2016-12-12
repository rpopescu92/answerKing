(function() {
    'use strict';

    angular.module('answerKingApp')
            .service('ItemService', ItemService);

    ItemService.$inject = ['$http'];

    function ItemService($http) {
        return{
            addItem : addItem
        }

        function addItem(data) {
            console.log(data);
            return $http({
                url: '/items',
                data: data,
                method: 'POST'

            });
        }
    }
})();