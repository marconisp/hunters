import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUser1, User1 } from '../user-1.model';
import { User1Service } from '../service/user-1.service';

@Injectable({ providedIn: 'root' })
export class User1RoutingResolveService implements Resolve<IUser1> {
  constructor(protected service: User1Service, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUser1> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((user1: HttpResponse<User1>) => {
          if (user1.body) {
            return of(user1.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new User1());
  }
}
