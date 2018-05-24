(function() {
    'use strict';

    angular
        .module('escribanosApp')
        .controller('EscribaniaDeleteController',EscribaniaDeleteController);

    EscribaniaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Escribania'];

    function EscribaniaDeleteController($uibModalInstance, entity, Escribania) {
        var vm = this;

        vm.escribania = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Escribania.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
