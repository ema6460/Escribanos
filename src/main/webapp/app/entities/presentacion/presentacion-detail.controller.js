(function() {
    'use strict';

    angular
        .module('escribanosApp')
        .controller('PresentacionDetailController', PresentacionDetailController);

    PresentacionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Presentacion', 'ArchivosDetalle', 'Escribania'];

    function PresentacionDetailController($scope, $rootScope, $stateParams, previousState, entity, Presentacion, ArchivosDetalle, Escribania) {
        var vm = this;

        vm.presentacion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('escribanosApp:presentacionUpdate', function(event, result) {
            vm.presentacion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
