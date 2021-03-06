jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IFollow, Follow } from '../follow.model';
import { FollowService } from '../service/follow.service';

import { FollowRoutingResolveService } from './follow-routing-resolve.service';

describe('Service Tests', () => {
  describe('Follow routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: FollowRoutingResolveService;
    let service: FollowService;
    let resultFollow: IFollow | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(FollowRoutingResolveService);
      service = TestBed.inject(FollowService);
      resultFollow = undefined;
    });

    describe('resolve', () => {
      it('should return IFollow returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFollow = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFollow).toEqual({ id: 123 });
      });

      it('should return new IFollow if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFollow = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultFollow).toEqual(new Follow());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Follow })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFollow = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFollow).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
