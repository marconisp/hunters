import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ITipoMensagem, TipoMensagem } from '../tipo-mensagem.model';
import { TipoMensagemService } from '../service/tipo-mensagem.service';

import { TipoMensagemRoutingResolveService } from './tipo-mensagem-routing-resolve.service';

describe('TipoMensagem routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TipoMensagemRoutingResolveService;
  let service: TipoMensagemService;
  let resultTipoMensagem: ITipoMensagem | undefined;

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
    routingResolveService = TestBed.inject(TipoMensagemRoutingResolveService);
    service = TestBed.inject(TipoMensagemService);
    resultTipoMensagem = undefined;
  });

  describe('resolve', () => {
    it('should return ITipoMensagem returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTipoMensagem = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTipoMensagem).toEqual({ id: 123 });
    });

    it('should return new ITipoMensagem if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTipoMensagem = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTipoMensagem).toEqual(new TipoMensagem());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TipoMensagem })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTipoMensagem = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTipoMensagem).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
