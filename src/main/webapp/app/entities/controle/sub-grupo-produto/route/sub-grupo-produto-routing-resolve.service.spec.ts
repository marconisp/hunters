import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISubGrupoProduto, SubGrupoProduto } from '../sub-grupo-produto.model';
import { SubGrupoProdutoService } from '../service/sub-grupo-produto.service';

import { SubGrupoProdutoRoutingResolveService } from './sub-grupo-produto-routing-resolve.service';

describe('SubGrupoProduto routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SubGrupoProdutoRoutingResolveService;
  let service: SubGrupoProdutoService;
  let resultSubGrupoProduto: ISubGrupoProduto | undefined;

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
    routingResolveService = TestBed.inject(SubGrupoProdutoRoutingResolveService);
    service = TestBed.inject(SubGrupoProdutoService);
    resultSubGrupoProduto = undefined;
  });

  describe('resolve', () => {
    it('should return ISubGrupoProduto returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSubGrupoProduto = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSubGrupoProduto).toEqual({ id: 123 });
    });

    it('should return new ISubGrupoProduto if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSubGrupoProduto = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSubGrupoProduto).toEqual(new SubGrupoProduto());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as SubGrupoProduto })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSubGrupoProduto = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSubGrupoProduto).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
