(function() {
    'use strict';

    angular
        .module('escribanosApp')
        .controller('EscribaniaController', EscribaniaController);

    EscribaniaController.$inject = ['Escribania'];

    function EscribaniaController(Escribania) {

        var vm = this;

        vm.escribanias = [];

        loadAll();

        function loadAll() {
            Escribania.query(function(result) {
                vm.escribanias = result;
                vm.searchQuery = null;
            });
        }
    }
})();
