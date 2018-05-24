'use strict';

describe('Controller Tests', function() {

    describe('Escribania Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEscribania, MockUser, MockPresentacion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEscribania = jasmine.createSpy('MockEscribania');
            MockUser = jasmine.createSpy('MockUser');
            MockPresentacion = jasmine.createSpy('MockPresentacion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Escribania': MockEscribania,
                'User': MockUser,
                'Presentacion': MockPresentacion
            };
            createController = function() {
                $injector.get('$controller')("EscribaniaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'escribanosApp:escribaniaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
