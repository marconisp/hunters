import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPeriodoDuracao, PeriodoDuracao } from '../periodo-duracao.model';
import { PeriodoDuracaoService } from '../service/periodo-duracao.service';

import { PeriodoDuracaoRoutingResolveService } from './periodo-duracao-routing-resolve.service';

describe('PeriodoDuracao routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PeriodoDuracaoRoutingResolveService;
  let service: PeriodoDuracaoService;
  let resultPeriodoDuracao: IPeriodoDuracao | undefined;

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
    routingResolveService = TestBed.inject(PeriodoDuracaoRoutingResolveService);
    service = TestBed.inject(PeriodoDuracaoService);
    resultPeriodoDuracao = undefined;
  });

  describe('resolve', () => {
    it('should return IPeriodoDuracao returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPeriodoDuracao = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPeriodoDuracao).toEqual({ id: 123 });
    });

    it('should return new IPeriodoDuracao if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPeriodoDuracao = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPeriodoDuracao).toEqual(new PeriodoDuracao());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PeriodoDuracao })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPeriodoDuracao = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPeriodoDuracao).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
