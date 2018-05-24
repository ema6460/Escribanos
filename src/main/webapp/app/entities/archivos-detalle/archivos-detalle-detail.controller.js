(function() {
    'use strict';

    angular
        .module('escribanosApp')
        .controller('ArchivosDetalleDetailController', ArchivosDetalleDetailController);

    ArchivosDetalleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ArchivosDetalle', 'Tramite', 'Presentacion'];

    function ArchivosDetalleDetailController($scope, $rootScope, $stateParams, previousState, entity, ArchivosDetalle, Tramite, Presentacion) {
        var vm = this;

        vm.archivosDetalle = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('escribanosApp:archivosDetalleUpdate', function(event, result) {
            vm.archivosDetalle = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
