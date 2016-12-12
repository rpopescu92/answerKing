(function() {
    'use strict';

    angular.module('answerKingApp')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', '$urlRouterProvider'];

    function stateConfig($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/home');
        $stateProvider.state('home', {
            url: '/home',
            parent: 'app',
            views: {
                'content@': {
                    templateUrl: 'app/home/home.html',
                    controller: 'HomeController'
                }
            }
        });
    }
})();