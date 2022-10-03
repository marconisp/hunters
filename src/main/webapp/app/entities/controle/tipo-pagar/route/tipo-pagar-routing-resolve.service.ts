import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITipoPagar, TipoPagar } from '../tipo-pagar.model';
import { TipoPagarService } from '../service/tipo-pagar.service';

@Injectable({ providedIn: 'root' })
export class TipoPagarRoutingResolveService implements Resolve<ITipoPagar> {
  constructor(protected service: TipoPagarService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoPagar> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tipoPagar: HttpResponse<TipoPagar>) => {
          if (tipoPagar.body) {
            return of(tipoPagar.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoPagar());
  }
}
