import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IDadosMedico, DadosMedico } from '../dados-medico.model';
import { DadosMedicoService } from '../service/dados-medico.service';

import { DadosMedicoRoutingResolveService } from './dados-medico-routing-resolve.service';

describe('DadosMedico routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DadosMedicoRoutingResolveService;
  let service: DadosMedicoService;
  let resultDadosMedico: IDadosMedico | undefined;

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
    routingResolveService = TestBed.inject(DadosMedicoRoutingResolveService);
    service = TestBed.inject(DadosMedicoService);
    resultDadosMedico = undefined;
  });

  describe('resolve', () => {
    it('should return IDadosMedico returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDadosMedico = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDadosMedico).toEqual({ id: 123 });
    });

    it('should return new IDadosMedico if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDadosMedico = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDadosMedico).toEqual(new DadosMedico());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DadosMedico })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDadosMedico = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDadosMedico).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
