import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITipoReceber, TipoReceber } from '../tipo-receber.model';
import { TipoReceberService } from '../service/tipo-receber.service';

@Injectable({ providedIn: 'root' })
export class TipoReceberRoutingResolveService implements Resolve<ITipoReceber> {
  constructor(protected service: TipoReceberService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoReceber> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tipoReceber: HttpResponse<TipoReceber>) => {
          if (tipoReceber.body) {
            return of(tipoReceber.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoReceber());
  }
}
