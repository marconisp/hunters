import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ITipoPessoa, TipoPessoa } from '../tipo-pessoa.model';
import { TipoPessoaService } from '../service/tipo-pessoa.service';

import { TipoPessoaRoutingResolveService } from './tipo-pessoa-routing-resolve.service';

describe('TipoPessoa routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TipoPessoaRoutingResolveService;
  let service: TipoPessoaService;
  let resultTipoPessoa: ITipoPessoa | undefined;

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
    routingResolveService = TestBed.inject(TipoPessoaRoutingResolveService);
    service = TestBed.inject(TipoPessoaService);
    resultTipoPessoa = undefined;
  });

  describe('resolve', () => {
    it('should return ITipoPessoa returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTipoPessoa = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTipoPessoa).toEqual({ id: 123 });
    });

    it('should return new ITipoPessoa if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTipoPessoa = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTipoPessoa).toEqual(new TipoPessoa());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TipoPessoa })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTipoPessoa = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTipoPessoa).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
