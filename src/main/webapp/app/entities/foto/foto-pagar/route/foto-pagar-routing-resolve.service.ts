import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFotoPagar, FotoPagar } from '../foto-pagar.model';
import { FotoPagarService } from '../service/foto-pagar.service';

@Injectable({ providedIn: 'root' })
export class FotoPagarRoutingResolveService implements Resolve<IFotoPagar> {
  constructor(protected service: FotoPagarService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFotoPagar> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fotoPagar: HttpResponse<FotoPagar>) => {
          if (fotoPagar.body) {
            return of(fotoPagar.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FotoPagar());
  }
}
