(function() {
    'use strict';

    angular
        .module('escribanosApp')
        .controller('EscribaniaDialogController', EscribaniaDialogController);

    EscribaniaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Escribania', 'User', 'Presentacion'];

    function EscribaniaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Escribania, User, Presentacion) {
        var vm = this;

        vm.escribania = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.presentacions = Presentacion.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.escribania.id !== null) {
                Escribania.update(vm.escribania, onSaveSuccess, onSaveError);
            } else {
                Escribania.save(vm.escribania, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('escribanosApp:escribaniaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
