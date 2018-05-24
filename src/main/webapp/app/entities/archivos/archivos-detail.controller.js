(function() {
    'use strict';

    angular
        .module('escribanosApp')
        .controller('ArchivosDetailController', ArchivosDetailController);

    ArchivosDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Archivos', 'Tramite'];

    function ArchivosDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Archivos, Tramite) {
        var vm = this;

        vm.archivos = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('escribanosApp:archivosUpdate', function(event, result) {
            vm.archivos = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
