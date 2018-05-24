(function() {
    'use strict';

    angular
        .module('escribanosApp')
        .controller('TramiteDeleteController',TramiteDeleteController);

    TramiteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tramite'];

    function TramiteDeleteController($uibModalInstance, entity, Tramite) {
        var vm = this;

        vm.tramite = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tramite.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
