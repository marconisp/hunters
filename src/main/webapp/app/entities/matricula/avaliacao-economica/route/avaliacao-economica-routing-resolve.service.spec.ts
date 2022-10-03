import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IAvaliacaoEconomica, AvaliacaoEconomica } from '../avaliacao-economica.model';
import { AvaliacaoEconomicaService } from '../service/avaliacao-economica.service';

import { AvaliacaoEconomicaRoutingResolveService } from './avaliacao-economica-routing-resolve.service';

describe('AvaliacaoEconomica routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AvaliacaoEconomicaRoutingResolveService;
  let service: AvaliacaoEconomicaService;
  let resultAvaliacaoEconomica: IAvaliacaoEconomica | undefined;

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
    routingResolveService = TestBed.inject(AvaliacaoEconomicaRoutingResolveService);
    service = TestBed.inject(AvaliacaoEconomicaService);
    resultAvaliacaoEconomica = undefined;
  });

  describe('resolve', () => {
    it('should return IAvaliacaoEconomica returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAvaliacaoEconomica = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAvaliacaoEconomica).toEqual({ id: 123 });
    });

    it('should return new IAvaliacaoEconomica if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAvaliacaoEconomica = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAvaliacaoEconomica).toEqual(new AvaliacaoEconomica());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as AvaliacaoEconomica })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAvaliacaoEconomica = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAvaliacaoEconomica).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
