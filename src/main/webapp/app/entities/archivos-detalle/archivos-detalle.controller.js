(function() {
    'use strict';

    angular
        .module('escribanosApp')
        .controller('ArchivosDetalleController', ArchivosDetalleController);

    ArchivosDetalleController.$inject = ['ArchivosDetalle'];

    function ArchivosDetalleController(ArchivosDetalle) {

        var vm = this;

        vm.archivosDetalles = [];

        loadAll();

        function loadAll() {
            ArchivosDetalle.query(function(result) {
                vm.archivosDetalles = result;
                vm.searchQuery = null;
            });
        }
    }
})();
