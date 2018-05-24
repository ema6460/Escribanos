(function() {
    'use strict';

    angular
        .module('escribanosApp')
        .controller('ArchivosController', ArchivosController);

    ArchivosController.$inject = ['DataUtils', 'Archivos'];

    function ArchivosController(DataUtils, Archivos) {

        var vm = this;

        vm.archivos = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Archivos.query(function(result) {
                vm.archivos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
