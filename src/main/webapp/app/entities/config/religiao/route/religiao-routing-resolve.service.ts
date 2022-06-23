import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IReligiao, Religiao } from '../religiao.model';
import { ReligiaoService } from '../service/religiao.service';

@Injectable({ providedIn: 'root' })
export class ReligiaoRoutingResolveService implements Resolve<IReligiao> {
  constructor(protected service: ReligiaoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReligiao> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((religiao: HttpResponse<Religiao>) => {
          if (religiao.body) {
            return of(religiao.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Religiao());
  }
}
