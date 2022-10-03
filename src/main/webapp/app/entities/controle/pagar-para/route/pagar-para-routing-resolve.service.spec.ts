import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPagarPara, PagarPara } from '../pagar-para.model';
import { PagarParaService } from '../service/pagar-para.service';

import { PagarParaRoutingResolveService } from './pagar-para-routing-resolve.service';

describe('PagarPara routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PagarParaRoutingResolveService;
  let service: PagarParaService;
  let resultPagarPara: IPagarPara | undefined;

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
    routingResolveService = TestBed.inject(PagarParaRoutingResolveService);
    service = TestBed.inject(PagarParaService);
    resultPagarPara = undefined;
  });

  describe('resolve', () => {
    it('should return IPagarPara returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPagarPara = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPagarPara).toEqual({ id: 123 });
    });

    it('should return new IPagarPara if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPagarPara = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPagarPara).toEqual(new PagarPara());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PagarPara })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPagarPara = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPagarPara).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
