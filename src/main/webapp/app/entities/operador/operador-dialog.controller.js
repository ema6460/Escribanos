(function() {
    'use strict';

    angular
        .module('escribanosApp')
        .controller('OperadorDialogController', OperadorDialogController);

    OperadorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Operador', 'User', 'Tramite'];

    function OperadorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Operador, User, Tramite) {
        var vm = this;

        vm.operador = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.tramites = Tramite.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.operador.id !== null) {
                Operador.update(vm.operador, onSaveSuccess, onSaveError);
            } else {
                Operador.save(vm.operador, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('escribanosApp:operadorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
