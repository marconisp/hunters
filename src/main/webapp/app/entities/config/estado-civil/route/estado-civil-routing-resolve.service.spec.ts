import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IEstadoCivil, EstadoCivil } from '../estado-civil.model';
import { EstadoCivilService } from '../service/estado-civil.service';

import { EstadoCivilRoutingResolveService } from './estado-civil-routing-resolve.service';

describe('EstadoCivil routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: EstadoCivilRoutingResolveService;
  let service: EstadoCivilService;
  let resultEstadoCivil: IEstadoCivil | undefined;

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
    routingResolveService = TestBed.inject(EstadoCivilRoutingResolveService);
    service = TestBed.inject(EstadoCivilService);
    resultEstadoCivil = undefined;
  });

  describe('resolve', () => {
    it('should return IEstadoCivil returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEstadoCivil = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEstadoCivil).toEqual({ id: 123 });
    });

    it('should return new IEstadoCivil if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEstadoCivil = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultEstadoCivil).toEqual(new EstadoCivil());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as EstadoCivil })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEstadoCivil = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEstadoCivil).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
