import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ICaracteristicasPsiquicas, CaracteristicasPsiquicas } from '../caracteristicas-psiquicas.model';
import { CaracteristicasPsiquicasService } from '../service/caracteristicas-psiquicas.service';

import { CaracteristicasPsiquicasRoutingResolveService } from './caracteristicas-psiquicas-routing-resolve.service';

describe('CaracteristicasPsiquicas routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: CaracteristicasPsiquicasRoutingResolveService;
  let service: CaracteristicasPsiquicasService;
  let resultCaracteristicasPsiquicas: ICaracteristicasPsiquicas | undefined;

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
    routingResolveService = TestBed.inject(CaracteristicasPsiquicasRoutingResolveService);
    service = TestBed.inject(CaracteristicasPsiquicasService);
    resultCaracteristicasPsiquicas = undefined;
  });

  describe('resolve', () => {
    it('should return ICaracteristicasPsiquicas returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCaracteristicasPsiquicas = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCaracteristicasPsiquicas).toEqual({ id: 123 });
    });

    it('should return new ICaracteristicasPsiquicas if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCaracteristicasPsiquicas = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCaracteristicasPsiquicas).toEqual(new CaracteristicasPsiquicas());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as CaracteristicasPsiquicas })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCaracteristicasPsiquicas = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCaracteristicasPsiquicas).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
