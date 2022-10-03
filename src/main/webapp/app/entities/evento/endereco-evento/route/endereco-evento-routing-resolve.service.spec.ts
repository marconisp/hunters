import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IEnderecoEvento, EnderecoEvento } from '../endereco-evento.model';
import { EnderecoEventoService } from '../service/endereco-evento.service';

import { EnderecoEventoRoutingResolveService } from './endereco-evento-routing-resolve.service';

describe('EnderecoEvento routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: EnderecoEventoRoutingResolveService;
  let service: EnderecoEventoService;
  let resultEnderecoEvento: IEnderecoEvento | undefined;

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
    routingResolveService = TestBed.inject(EnderecoEventoRoutingResolveService);
    service = TestBed.inject(EnderecoEventoService);
    resultEnderecoEvento = undefined;
  });

  describe('resolve', () => {
    it('should return IEnderecoEvento returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEnderecoEvento = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEnderecoEvento).toEqual({ id: 123 });
    });

    it('should return new IEnderecoEvento if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEnderecoEvento = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultEnderecoEvento).toEqual(new EnderecoEvento());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as EnderecoEvento })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEnderecoEvento = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEnderecoEvento).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
