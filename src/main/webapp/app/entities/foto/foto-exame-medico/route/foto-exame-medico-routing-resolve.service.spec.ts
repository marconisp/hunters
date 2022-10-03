import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IFotoExameMedico, FotoExameMedico } from '../foto-exame-medico.model';
import { FotoExameMedicoService } from '../service/foto-exame-medico.service';

import { FotoExameMedicoRoutingResolveService } from './foto-exame-medico-routing-resolve.service';

describe('FotoExameMedico routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: FotoExameMedicoRoutingResolveService;
  let service: FotoExameMedicoService;
  let resultFotoExameMedico: IFotoExameMedico | undefined;

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
    routingResolveService = TestBed.inject(FotoExameMedicoRoutingResolveService);
    service = TestBed.inject(FotoExameMedicoService);
    resultFotoExameMedico = undefined;
  });

  describe('resolve', () => {
    it('should return IFotoExameMedico returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFotoExameMedico = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultFotoExameMedico).toEqual({ id: 123 });
    });

    it('should return new IFotoExameMedico if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFotoExameMedico = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultFotoExameMedico).toEqual(new FotoExameMedico());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as FotoExameMedico })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFotoExameMedico = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultFotoExameMedico).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
