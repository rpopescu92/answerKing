(function (){
    'use strict';

    angular.module('answerKingApp')
            .controller('ItemController', ItemController);

    ItemController.$inject = ['$scope', 'ItemService', '$mdDialog', '$rootScope'];

    function ItemController($scope, ItemService, $mdDialog, $rootScope) {
        $scope.addNewItem = addNewItem;
        $scope.closeDialog = closeDialog;


        function addNewItem() {
            var item = {
                        name: $scope.name,
                        price: $scope.price
                    };
            console.log(item);
            ItemService.addItem(item)
                        .then(function(data) {
                            $mdDialog.hide();
                            $rootScope.$emit('refresh-data');
                        },
                            function(error) {

          } );
       }

       function closeDialog() {
            $mdDialog.hide();
       }
    }
})()