import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IFotoAvatar, FotoAvatar } from '../foto-avatar.model';
import { FotoAvatarService } from '../service/foto-avatar.service';

import { FotoAvatarRoutingResolveService } from './foto-avatar-routing-resolve.service';

describe('FotoAvatar routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: FotoAvatarRoutingResolveService;
  let service: FotoAvatarService;
  let resultFotoAvatar: IFotoAvatar | undefined;

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
    routingResolveService = TestBed.inject(FotoAvatarRoutingResolveService);
    service = TestBed.inject(FotoAvatarService);
    resultFotoAvatar = undefined;
  });

  describe('resolve', () => {
    it('should return IFotoAvatar returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFotoAvatar = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultFotoAvatar).toEqual({ id: 123 });
    });

    it('should return new IFotoAvatar if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFotoAvatar = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultFotoAvatar).toEqual(new FotoAvatar());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as FotoAvatar })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFotoAvatar = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultFotoAvatar).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
