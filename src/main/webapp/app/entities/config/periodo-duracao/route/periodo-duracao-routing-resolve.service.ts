import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPeriodoDuracao, PeriodoDuracao } from '../periodo-duracao.model';
import { PeriodoDuracaoService } from '../service/periodo-duracao.service';

@Injectable({ providedIn: 'root' })
export class PeriodoDuracaoRoutingResolveService implements Resolve<IPeriodoDuracao> {
  constructor(protected service: PeriodoDuracaoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPeriodoDuracao> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((periodoDuracao: HttpResponse<PeriodoDuracao>) => {
          if (periodoDuracao.body) {
            return of(periodoDuracao.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PeriodoDuracao());
  }
}
