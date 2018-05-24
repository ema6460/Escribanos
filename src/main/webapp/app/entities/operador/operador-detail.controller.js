(function() {
    'use strict';

    angular
        .module('escribanosApp')
        .controller('OperadorDetailController', OperadorDetailController);

    OperadorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Operador', 'User', 'Tramite'];

    function OperadorDetailController($scope, $rootScope, $stateParams, previousState, entity, Operador, User, Tramite) {
        var vm = this;

        vm.operador = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('escribanosApp:operadorUpdate', function(event, result) {
            vm.operador = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
