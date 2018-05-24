(function() {
    'use strict';

    angular
        .module('escribanosApp')
        .controller('EscribaniaDetailController', EscribaniaDetailController);

    EscribaniaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Escribania', 'User', 'Presentacion'];

    function EscribaniaDetailController($scope, $rootScope, $stateParams, previousState, entity, Escribania, User, Presentacion) {
        var vm = this;

        vm.escribania = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('escribanosApp:escribaniaUpdate', function(event, result) {
            vm.escribania = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
