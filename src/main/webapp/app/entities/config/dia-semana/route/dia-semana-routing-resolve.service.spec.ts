import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IDiaSemana, DiaSemana } from '../dia-semana.model';
import { DiaSemanaService } from '../service/dia-semana.service';

import { DiaSemanaRoutingResolveService } from './dia-semana-routing-resolve.service';

describe('DiaSemana routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DiaSemanaRoutingResolveService;
  let service: DiaSemanaService;
  let resultDiaSemana: IDiaSemana | undefined;

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
    routingResolveService = TestBed.inject(DiaSemanaRoutingResolveService);
    service = TestBed.inject(DiaSemanaService);
    resultDiaSemana = undefined;
  });

  describe('resolve', () => {
    it('should return IDiaSemana returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDiaSemana = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDiaSemana).toEqual({ id: 123 });
    });

    it('should return new IDiaSemana if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDiaSemana = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDiaSemana).toEqual(new DiaSemana());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DiaSemana })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDiaSemana = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDiaSemana).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
