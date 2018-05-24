'use strict';

describe('Controller Tests', function() {

    describe('Tramite Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTramite, MockArchivos, MockArchivosDetalle, MockOperador;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTramite = jasmine.createSpy('MockTramite');
            MockArchivos = jasmine.createSpy('MockArchivos');
            MockArchivosDetalle = jasmine.createSpy('MockArchivosDetalle');
            MockOperador = jasmine.createSpy('MockOperador');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Tramite': MockTramite,
                'Archivos': MockArchivos,
                'ArchivosDetalle': MockArchivosDetalle,
                'Operador': MockOperador
            };
            createController = function() {
                $injector.get('$controller')("TramiteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'escribanosApp:tramiteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
