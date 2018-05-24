(function() {
    'use strict';

    angular
        .module('escribanosApp')
        .controller('OperadorController', OperadorController);

    OperadorController.$inject = ['Operador'];

    function OperadorController(Operador) {

        var vm = this;

        vm.operadors = [];

        loadAll();

        function loadAll() {
            Operador.query(function(result) {
                vm.operadors = result;
                vm.searchQuery = null;
            });
        }
    }
})();
