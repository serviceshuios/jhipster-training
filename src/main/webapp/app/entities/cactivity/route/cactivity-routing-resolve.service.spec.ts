jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICactivity, Cactivity } from '../cactivity.model';
import { CactivityService } from '../service/cactivity.service';

import { CactivityRoutingResolveService } from './cactivity-routing-resolve.service';

describe('Service Tests', () => {
  describe('Cactivity routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CactivityRoutingResolveService;
    let service: CactivityService;
    let resultCactivity: ICactivity | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CactivityRoutingResolveService);
      service = TestBed.inject(CactivityService);
      resultCactivity = undefined;
    });

    describe('resolve', () => {
      it('should return ICactivity returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCactivity = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCactivity).toEqual({ id: 123 });
      });

      it('should return new ICactivity if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCactivity = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCactivity).toEqual(new Cactivity());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Cactivity })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCactivity = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCactivity).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
