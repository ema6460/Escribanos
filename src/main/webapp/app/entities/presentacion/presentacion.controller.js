(function() {
    'use strict';

    angular
        .module('escribanosApp')
        .controller('PresentacionController', PresentacionController);

    PresentacionController.$inject = ['Presentacion'];

    function PresentacionController(Presentacion) {

        var vm = this;

        vm.presentacions = [];

        loadAll();

        function loadAll() {
            Presentacion.query(function(result) {
                vm.presentacions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
