(function() {
    'use strict';
    angular
        .module('escribanosApp')
        .factory('Escribania', Escribania);

    Escribania.$inject = ['$resource'];

    function Escribania ($resource) {
        var resourceUrl =  'api/escribanias/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
