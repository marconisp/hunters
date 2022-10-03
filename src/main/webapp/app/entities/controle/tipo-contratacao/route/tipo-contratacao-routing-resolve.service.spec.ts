import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ITipoContratacao, TipoContratacao } from '../tipo-contratacao.model';
import { TipoContratacaoService } from '../service/tipo-contratacao.service';

import { TipoContratacaoRoutingResolveService } from './tipo-contratacao-routing-resolve.service';

describe('TipoContratacao routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TipoContratacaoRoutingResolveService;
  let service: TipoContratacaoService;
  let resultTipoContratacao: ITipoContratacao | undefined;

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
    routingResolveService = TestBed.inject(TipoContratacaoRoutingResolveService);
    service = TestBed.inject(TipoContratacaoService);
    resultTipoContratacao = undefined;
  });

  describe('resolve', () => {
    it('should return ITipoContratacao returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTipoContratacao = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTipoContratacao).toEqual({ id: 123 });
    });

    it('should return new ITipoContratacao if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTipoContratacao = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTipoContratacao).toEqual(new TipoContratacao());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TipoContratacao })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTipoContratacao = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTipoContratacao).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
