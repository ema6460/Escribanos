(function() {
    'use strict';
    angular
        .module('escribanosApp')
        .factory('ArchivosDetalle', ArchivosDetalle);

    ArchivosDetalle.$inject = ['$resource'];

    function ArchivosDetalle ($resource) {
        var resourceUrl =  'api/archivos-detalles/:id';

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
