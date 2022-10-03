import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IAgendaColaborador, AgendaColaborador } from '../agenda-colaborador.model';
import { AgendaColaboradorService } from '../service/agenda-colaborador.service';

import { AgendaColaboradorRoutingResolveService } from './agenda-colaborador-routing-resolve.service';

describe('AgendaColaborador routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AgendaColaboradorRoutingResolveService;
  let service: AgendaColaboradorService;
  let resultAgendaColaborador: IAgendaColaborador | undefined;

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
    routingResolveService = TestBed.inject(AgendaColaboradorRoutingResolveService);
    service = TestBed.inject(AgendaColaboradorService);
    resultAgendaColaborador = undefined;
  });

  describe('resolve', () => {
    it('should return IAgendaColaborador returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAgendaColaborador = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAgendaColaborador).toEqual({ id: 123 });
    });

    it('should return new IAgendaColaborador if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAgendaColaborador = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAgendaColaborador).toEqual(new AgendaColaborador());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as AgendaColaborador })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAgendaColaborador = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAgendaColaborador).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
