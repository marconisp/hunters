import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IFotoPagar, FotoPagar } from '../foto-pagar.model';
import { FotoPagarService } from '../service/foto-pagar.service';

import { FotoPagarRoutingResolveService } from './foto-pagar-routing-resolve.service';

describe('FotoPagar routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: FotoPagarRoutingResolveService;
  let service: FotoPagarService;
  let resultFotoPagar: IFotoPagar | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(FotoPagarRoutingResolveService);
    service = TestBed.inject(FotoPagarService);
    resultFotoPagar = undefined;
  });

  describe('resolve', () => {
    it('should return IFotoPagar returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFotoPagar = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultFotoPagar).toEqual({ id: 123 });
    });

    it('should return new IFotoPagar if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFotoPagar = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultFotoPagar).toEqual(new FotoPagar());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as FotoPagar })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFotoPagar = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultFotoPagar).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
