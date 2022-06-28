import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IFotoIcon, FotoIcon } from '../foto-icon.model';
import { FotoIconService } from '../service/foto-icon.service';

import { FotoIconRoutingResolveService } from './foto-icon-routing-resolve.service';

describe('FotoIcon routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: FotoIconRoutingResolveService;
  let service: FotoIconService;
  let resultFotoIcon: IFotoIcon | undefined;

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
    routingResolveService = TestBed.inject(FotoIconRoutingResolveService);
    service = TestBed.inject(FotoIconService);
    resultFotoIcon = undefined;
  });

  describe('resolve', () => {
    it('should return IFotoIcon returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFotoIcon = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultFotoIcon).toEqual({ id: 123 });
    });

    it('should return new IFotoIcon if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFotoIcon = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultFotoIcon).toEqual(new FotoIcon());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as FotoIcon })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFotoIcon = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultFotoIcon).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
