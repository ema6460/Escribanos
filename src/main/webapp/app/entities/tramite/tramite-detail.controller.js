(function() {
    'use strict';

    angular
        .module('escribanosApp')
        .controller('TramiteDetailController', TramiteDetailController);

    TramiteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tramite', 'Archivos', 'ArchivosDetalle', 'DataUtils','Operador'];

    function TramiteDetailController($scope, $rootScope, $stateParams, previousState, entity, Tramite, Archivos, ArchivosDetalle, Operador, DataUtils) {
        var vm = this;
        
        vm.tramites = [];
        vm.tramite = entity;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.previousState = previousState.name;
        
        vm.openAlert = function (){
            window.alert("hola");
        };
        
        
        
//        console.log(vm.tramite);
//        console.log(vm.tramite.archivos);
//        console.log(vm.tramite.archivos[0]);
//        console.log(vm.tramite.archivos[0].id);
//        console.log(vm.tramite.archivos[0].archivo);
        
        var unsubscribe = $rootScope.$on('escribanosApp:tramiteUpdate', function(event, result) {
            vm.tramite = result;
        });
        $scope.$on('$destroy', unsubscribe);
        
        
        
        
    }
})();
