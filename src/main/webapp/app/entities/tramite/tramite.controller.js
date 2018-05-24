(function() {
    'use strict';

    angular
        .module('escribanosApp')
        .controller('TramiteController', TramiteController);

    TramiteController.$inject = ['Tramite', 'DataUtils'];

    function TramiteController(Tramite, DataUtils) {

        var vm = this;

        vm.tramites = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Tramite.query(function(result) {
                vm.tramites = result;
                vm.searchQuery = null;
            });
        }
    }
})();
