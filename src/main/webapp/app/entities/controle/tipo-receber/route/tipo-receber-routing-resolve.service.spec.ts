import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ITipoReceber, TipoReceber } from '../tipo-receber.model';
import { TipoReceberService } from '../service/tipo-receber.service';

import { TipoReceberRoutingResolveService } from './tipo-receber-routing-resolve.service';

describe('TipoReceber routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TipoReceberRoutingResolveService;
  let service: TipoReceberService;
  let resultTipoReceber: ITipoReceber | undefined;

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
    routingResolveService = TestBed.inject(TipoReceberRoutingResolveService);
    service = TestBed.inject(TipoReceberService);
    resultTipoReceber = undefined;
  });

  describe('resolve', () => {
    it('should return ITipoReceber returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTipoReceber = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTipoReceber).toEqual({ id: 123 });
    });

    it('should return new ITipoReceber if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTipoReceber = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTipoReceber).toEqual(new TipoReceber());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TipoReceber })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTipoReceber = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTipoReceber).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
