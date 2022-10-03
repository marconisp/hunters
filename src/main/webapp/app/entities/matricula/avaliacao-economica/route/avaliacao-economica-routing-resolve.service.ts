import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAvaliacaoEconomica, AvaliacaoEconomica } from '../avaliacao-economica.model';
import { AvaliacaoEconomicaService } from '../service/avaliacao-economica.service';

@Injectable({ providedIn: 'root' })
export class AvaliacaoEconomicaRoutingResolveService implements Resolve<IAvaliacaoEconomica> {
  constructor(protected service: AvaliacaoEconomicaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAvaliacaoEconomica> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((avaliacaoEconomica: HttpResponse<AvaliacaoEconomica>) => {
          if (avaliacaoEconomica.body) {
            return of(avaliacaoEconomica.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AvaliacaoEconomica());
  }
}
