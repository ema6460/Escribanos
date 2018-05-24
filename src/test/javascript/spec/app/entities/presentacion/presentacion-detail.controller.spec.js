'use strict';

describe('Controller Tests', function() {

    describe('Presentacion Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPresentacion, MockArchivosDetalle, MockEscribania;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPresentacion = jasmine.createSpy('MockPresentacion');
            MockArchivosDetalle = jasmine.createSpy('MockArchivosDetalle');
            MockEscribania = jasmine.createSpy('MockEscribania');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Presentacion': MockPresentacion,
                'ArchivosDetalle': MockArchivosDetalle,
                'Escribania': MockEscribania
            };
            createController = function() {
                $injector.get('$controller')("PresentacionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'escribanosApp:presentacionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
