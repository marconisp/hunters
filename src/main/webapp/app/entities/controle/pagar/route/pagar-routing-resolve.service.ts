import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPagar, Pagar } from '../pagar.model';
import { PagarService } from '../service/pagar.service';

@Injectable({ providedIn: 'root' })
export class PagarRoutingResolveService implements Resolve<IPagar> {
  constructor(protected service: PagarService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPagar> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pagar: HttpResponse<Pagar>) => {
          if (pagar.body) {
            return of(pagar.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Pagar());
  }
}
