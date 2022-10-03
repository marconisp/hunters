import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IGrupoProduto, GrupoProduto } from '../grupo-produto.model';
import { GrupoProdutoService } from '../service/grupo-produto.service';

import { GrupoProdutoRoutingResolveService } from './grupo-produto-routing-resolve.service';

describe('GrupoProduto routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: GrupoProdutoRoutingResolveService;
  let service: GrupoProdutoService;
  let resultGrupoProduto: IGrupoProduto | undefined;

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
    routingResolveService = TestBed.inject(GrupoProdutoRoutingResolveService);
    service = TestBed.inject(GrupoProdutoService);
    resultGrupoProduto = undefined;
  });

  describe('resolve', () => {
    it('should return IGrupoProduto returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultGrupoProduto = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultGrupoProduto).toEqual({ id: 123 });
    });

    it('should return new IGrupoProduto if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultGrupoProduto = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultGrupoProduto).toEqual(new GrupoProduto());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as GrupoProduto })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultGrupoProduto = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultGrupoProduto).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
