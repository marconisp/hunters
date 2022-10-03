import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IReceber, Receber } from '../receber.model';
import { ReceberService } from '../service/receber.service';

@Injectable({ providedIn: 'root' })
export class ReceberRoutingResolveService implements Resolve<IReceber> {
  constructor(protected service: ReceberService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReceber> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((receber: HttpResponse<Receber>) => {
          if (receber.body) {
            return of(receber.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Receber());
  }
}
