import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IAcompanhamentoAluno, AcompanhamentoAluno } from '../acompanhamento-aluno.model';
import { AcompanhamentoAlunoService } from '../service/acompanhamento-aluno.service';

import { AcompanhamentoAlunoRoutingResolveService } from './acompanhamento-aluno-routing-resolve.service';

describe('AcompanhamentoAluno routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AcompanhamentoAlunoRoutingResolveService;
  let service: AcompanhamentoAlunoService;
  let resultAcompanhamentoAluno: IAcompanhamentoAluno | undefined;

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
    routingResolveService = TestBed.inject(AcompanhamentoAlunoRoutingResolveService);
    service = TestBed.inject(AcompanhamentoAlunoService);
    resultAcompanhamentoAluno = undefined;
  });

  describe('resolve', () => {
    it('should return IAcompanhamentoAluno returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAcompanhamentoAluno = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAcompanhamentoAluno).toEqual({ id: 123 });
    });

    it('should return new IAcompanhamentoAluno if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAcompanhamentoAluno = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAcompanhamentoAluno).toEqual(new AcompanhamentoAluno());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as AcompanhamentoAluno })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAcompanhamentoAluno = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAcompanhamentoAluno).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
