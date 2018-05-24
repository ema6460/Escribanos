(function() {
    'use strict';

    angular
        .module('escribanosApp')
        .controller('ArchivosDetalleDialogController', ArchivosDetalleDialogController);

    ArchivosDetalleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ArchivosDetalle', 'Tramite', 'Presentacion'];

    function ArchivosDetalleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ArchivosDetalle, Tramite, Presentacion) {
        var vm = this;

        vm.archivosDetalle = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tramites = Tramite.query();
        vm.presentacions = Presentacion.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.archivosDetalle.id !== null) {
                ArchivosDetalle.update(vm.archivosDetalle, onSaveSuccess, onSaveError);
            } else {
                ArchivosDetalle.save(vm.archivosDetalle, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('escribanosApp:archivosDetalleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
