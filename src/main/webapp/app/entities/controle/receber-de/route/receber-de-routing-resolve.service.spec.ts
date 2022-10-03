import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IReceberDe, ReceberDe } from '../receber-de.model';
import { ReceberDeService } from '../service/receber-de.service';

import { ReceberDeRoutingResolveService } from './receber-de-routing-resolve.service';

describe('ReceberDe routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ReceberDeRoutingResolveService;
  let service: ReceberDeService;
  let resultReceberDe: IReceberDe | undefined;

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
    routingResolveService = TestBed.inject(ReceberDeRoutingResolveService);
    service = TestBed.inject(ReceberDeService);
    resultReceberDe = undefined;
  });

  describe('resolve', () => {
    it('should return IReceberDe returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultReceberDe = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultReceberDe).toEqual({ id: 123 });
    });

    it('should return new IReceberDe if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultReceberDe = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultReceberDe).toEqual(new ReceberDe());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ReceberDe })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultReceberDe = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultReceberDe).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
