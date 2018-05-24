(function() {
    'use strict';
    angular
        .module('escribanosApp')
        .factory('Archivos', Archivos);

    Archivos.$inject = ['$resource'];

    function Archivos ($resource) {
        var resourceUrl =  'api/archivos/:id';

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
