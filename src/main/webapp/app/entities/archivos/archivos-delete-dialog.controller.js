(function() {
    'use strict';

    angular
        .module('escribanosApp')
        .controller('ArchivosDeleteController',ArchivosDeleteController);

    ArchivosDeleteController.$inject = ['$uibModalInstance', 'entity', 'Archivos'];

    function ArchivosDeleteController($uibModalInstance, entity, Archivos) {
        var vm = this;

        vm.archivos = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Archivos.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
