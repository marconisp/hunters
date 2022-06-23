import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAviso, Aviso } from '../aviso.model';
import { AvisoService } from '../service/aviso.service';

@Injectable({ providedIn: 'root' })
export class AvisoRoutingResolveService implements Resolve<IAviso> {
  constructor(protected service: AvisoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAviso> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((aviso: HttpResponse<Aviso>) => {
          if (aviso.body) {
            return of(aviso.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Aviso());
  }
}
