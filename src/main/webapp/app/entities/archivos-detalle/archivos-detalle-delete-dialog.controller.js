(function() {
    'use strict';

    angular
        .module('escribanosApp')
        .controller('ArchivosDetalleDeleteController',ArchivosDetalleDeleteController);

    ArchivosDetalleDeleteController.$inject = ['$uibModalInstance', 'entity', 'ArchivosDetalle'];

    function ArchivosDetalleDeleteController($uibModalInstance, entity, ArchivosDetalle) {
        var vm = this;

        vm.archivosDetalle = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ArchivosDetalle.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
