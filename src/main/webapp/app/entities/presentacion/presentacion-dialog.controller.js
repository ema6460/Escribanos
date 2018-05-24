(function () {
    'use strict';

    angular
            .module('escribanosApp')
            .controller('PresentacionDialogController', PresentacionDialogController);

    PresentacionDialogController.$inject = ['$timeout', 'Principal', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Presentacion', 'ArchivosDetalle', 'Escribania', 'Archivos', 'DataUtils', 'entityArchivosDetalle', 'entityTramite', 'entityArchivos'];

    function PresentacionDialogController($timeout, Principal, $scope, $stateParams, $uibModalInstance, entity, Presentacion, ArchivosDetalle, Escribania, Archivos, DataUtils, entityArchivosDetalle, entityTramite, entityArchivos) {
        var vm = this;

        vm.archivos = {};
        vm.archivos1 = {};
        vm.archivos2 = {};
        vm.archivosList = [];
        vm.presentacion = entity;
        vm.clear = clear;
        vm.save = save;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.archivosdetalles = ArchivosDetalle.query();
        vm.escribanias = Escribania.query();
        vm.isArchivosDetalleCollapsed = true;
        console.log(Principal.identity()); 

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            if(vm.presentacion !== null){
                window.alert("ADVERTENCIA: Si cierra esta ventana perderá los datos!")
            }
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.presentacion.id !== null) {
                Presentacion.update(vm.presentacion, onSaveSuccess, onSaveError);
            } else {
                Presentacion.save(vm.presentacion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('escribanosApp:presentacionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }

        vm.setArchivo = function ($file, archivos) {
            if ($file) {
                if (vm.archivosList.length < 3) {
                    DataUtils.toBase64($file, function (base64Data) {
                        $scope.$apply(function () {
                            archivos.archivo = base64Data;
                            archivos.archivoContentType = $file.type;
                            vm.archivosList.push(archivos);
//                            console.log(vm.archivosList);

                        });
                    });
                    vm.presentacion.archivo = vm.archivosList;
                }
            }
        };

        vm.removeArchivo = function () {
            vm.archivosList.pop();
//            console.log(vm.archivosList);
        };





        function getAccount() {
            Principal.identity().then(function (account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
                console.log(Principal.identity())
            });
        }







    }
})();
