jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IUrllink, Urllink } from '../urllink.model';
import { UrllinkService } from '../service/urllink.service';

import { UrllinkRoutingResolveService } from './urllink-routing-resolve.service';

describe('Service Tests', () => {
  describe('Urllink routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: UrllinkRoutingResolveService;
    let service: UrllinkService;
    let resultUrllink: IUrllink | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(UrllinkRoutingResolveService);
      service = TestBed.inject(UrllinkService);
      resultUrllink = undefined;
    });

    describe('resolve', () => {
      it('should return IUrllink returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultUrllink = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultUrllink).toEqual({ id: 123 });
      });

      it('should return new IUrllink if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultUrllink = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultUrllink).toEqual(new Urllink());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Urllink })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultUrllink = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultUrllink).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
