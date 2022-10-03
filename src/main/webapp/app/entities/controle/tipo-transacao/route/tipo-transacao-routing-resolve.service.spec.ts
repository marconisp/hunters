import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ITipoTransacao, TipoTransacao } from '../tipo-transacao.model';
import { TipoTransacaoService } from '../service/tipo-transacao.service';

import { TipoTransacaoRoutingResolveService } from './tipo-transacao-routing-resolve.service';

describe('TipoTransacao routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TipoTransacaoRoutingResolveService;
  let service: TipoTransacaoService;
  let resultTipoTransacao: ITipoTransacao | undefined;

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
    routingResolveService = TestBed.inject(TipoTransacaoRoutingResolveService);
    service = TestBed.inject(TipoTransacaoService);
    resultTipoTransacao = undefined;
  });

  describe('resolve', () => {
    it('should return ITipoTransacao returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTipoTransacao = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTipoTransacao).toEqual({ id: 123 });
    });

    it('should return new ITipoTransacao if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTipoTransacao = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTipoTransacao).toEqual(new TipoTransacao());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TipoTransacao })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTipoTransacao = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTipoTransacao).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
