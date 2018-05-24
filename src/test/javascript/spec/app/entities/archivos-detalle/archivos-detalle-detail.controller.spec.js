'use strict';

describe('Controller Tests', function() {

    describe('ArchivosDetalle Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockArchivosDetalle, MockTramite, MockPresentacion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockArchivosDetalle = jasmine.createSpy('MockArchivosDetalle');
            MockTramite = jasmine.createSpy('MockTramite');
            MockPresentacion = jasmine.createSpy('MockPresentacion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ArchivosDetalle': MockArchivosDetalle,
                'Tramite': MockTramite,
                'Presentacion': MockPresentacion
            };
            createController = function() {
                $injector.get('$controller')("ArchivosDetalleDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'escribanosApp:archivosDetalleUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
