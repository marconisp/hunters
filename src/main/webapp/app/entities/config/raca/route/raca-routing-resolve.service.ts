import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRaca, Raca } from '../raca.model';
import { RacaService } from '../service/raca.service';

@Injectable({ providedIn: 'root' })
export class RacaRoutingResolveService implements Resolve<IRaca> {
  constructor(protected service: RacaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRaca> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((raca: HttpResponse<Raca>) => {
          if (raca.body) {
            return of(raca.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Raca());
  }
}
