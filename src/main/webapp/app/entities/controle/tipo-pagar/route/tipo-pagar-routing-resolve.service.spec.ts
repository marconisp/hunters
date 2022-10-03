import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ITipoPagar, TipoPagar } from '../tipo-pagar.model';
import { TipoPagarService } from '../service/tipo-pagar.service';

import { TipoPagarRoutingResolveService } from './tipo-pagar-routing-resolve.service';

describe('TipoPagar routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TipoPagarRoutingResolveService;
  let service: TipoPagarService;
  let resultTipoPagar: ITipoPagar | undefined;

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
    routingResolveService = TestBed.inject(TipoPagarRoutingResolveService);
    service = TestBed.inject(TipoPagarService);
    resultTipoPagar = undefined;
  });

  describe('resolve', () => {
    it('should return ITipoPagar returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTipoPagar = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTipoPagar).toEqual({ id: 123 });
    });

    it('should return new ITipoPagar if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTipoPagar = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTipoPagar).toEqual(new TipoPagar());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TipoPagar })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTipoPagar = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTipoPagar).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
