(function() {
    'use strict';

    angular
        .module('escribanosApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('archivos', {
            parent: 'entity',
            url: '/archivos',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'escribanosApp.archivos.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/archivos/archivos.html',
                    controller: 'ArchivosController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('archivos');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('archivos-detail', {
            parent: 'archivos',
            url: '/archivos/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'escribanosApp.archivos.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/archivos/archivos-detail.html',
                    controller: 'ArchivosDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('archivos');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Archivos', function($stateParams, Archivos) {
                    return Archivos.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'archivos',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('archivos-detail.edit', {
            parent: 'archivos-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/archivos/archivos-dialog.html',
                    controller: 'ArchivosDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Archivos', function(Archivos) {
                            return Archivos.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('archivos.new', {
            parent: 'archivos',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/archivos/archivos-dialog.html',
                    controller: 'ArchivosDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                archivo: null,
                                archivoContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('archivos', null, { reload: 'archivos' });
                }, function() {
                    $state.go('archivos');
                });
            }]
        })
        .state('archivos.edit', {
            parent: 'archivos',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/archivos/archivos-dialog.html',
                    controller: 'ArchivosDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Archivos', function(Archivos) {
                            return Archivos.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('archivos', null, { reload: 'archivos' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('archivos.delete', {
            parent: 'archivos',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/archivos/archivos-delete-dialog.html',
                    controller: 'ArchivosDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Archivos', function(Archivos) {
                            return Archivos.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('archivos', null, { reload: 'archivos' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
