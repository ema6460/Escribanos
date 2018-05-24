(function() {
    'use strict';

    angular
        .module('escribanosApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('archivos-detalle', {
            parent: 'entity',
            url: '/archivos-detalle',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'escribanosApp.archivosDetalle.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/archivos-detalle/archivos-detalles.html',
                    controller: 'ArchivosDetalleController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('archivosDetalle');
                    $translatePartialLoader.addPart('estadoPresentacion');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('archivos-detalle-detail', {
            parent: 'archivos-detalle',
            url: '/archivos-detalle/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'escribanosApp.archivosDetalle.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/archivos-detalle/archivos-detalle-detail.html',
                    controller: 'ArchivosDetalleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('archivosDetalle');
                    $translatePartialLoader.addPart('estadoPresentacion');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ArchivosDetalle', function($stateParams, ArchivosDetalle) {
                    return ArchivosDetalle.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'archivos-detalle',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('archivos-detalle-detail.edit', {
            parent: 'archivos-detalle-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/archivos-detalle/archivos-detalle-dialog.html',
                    controller: 'ArchivosDetalleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ArchivosDetalle', function(ArchivosDetalle) {
                            return ArchivosDetalle.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('archivos-detalle.new', {
            parent: 'archivos-detalle',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/archivos-detalle/archivos-detalle-dialog.html',
                    controller: 'ArchivosDetalleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                estado: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('archivos-detalle', null, { reload: 'archivos-detalle' });
                }, function() {
                    $state.go('archivos-detalle');
                });
            }]
        })
        .state('archivos-detalle.edit', {
            parent: 'archivos-detalle',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/archivos-detalle/archivos-detalle-dialog.html',
                    controller: 'ArchivosDetalleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ArchivosDetalle', function(ArchivosDetalle) {
                            return ArchivosDetalle.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('archivos-detalle', null, { reload: 'archivos-detalle' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('archivos-detalle.delete', {
            parent: 'archivos-detalle',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/archivos-detalle/archivos-detalle-delete-dialog.html',
                    controller: 'ArchivosDetalleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ArchivosDetalle', function(ArchivosDetalle) {
                            return ArchivosDetalle.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('archivos-detalle', null, { reload: 'archivos-detalle' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
