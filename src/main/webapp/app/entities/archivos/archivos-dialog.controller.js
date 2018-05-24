(function() {
    'use strict';

    angular
        .module('escribanosApp')
        .controller('ArchivosDialogController', ArchivosDialogController);

    ArchivosDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Archivos', 'Tramite'];

    function ArchivosDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Archivos, Tramite) {
        var vm = this;

        vm.archivos = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.tramites = Tramite.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.archivos.id !== null) {
                Archivos.update(vm.archivos, onSaveSuccess, onSaveError);
            } else {
                Archivos.save(vm.archivos, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('escribanosApp:archivosUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setArchivo = function ($file, archivos) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        archivos.archivo = base64Data;
                        archivos.archivoContentType = $file.type;
                    });
                });
            }
        };

    }
})();
