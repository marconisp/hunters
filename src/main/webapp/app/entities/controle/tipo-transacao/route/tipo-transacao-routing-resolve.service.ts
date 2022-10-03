import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITipoTransacao, TipoTransacao } from '../tipo-transacao.model';
import { TipoTransacaoService } from '../service/tipo-transacao.service';

@Injectable({ providedIn: 'root' })
export class TipoTransacaoRoutingResolveService implements Resolve<ITipoTransacao> {
  constructor(protected service: TipoTransacaoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoTransacao> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tipoTransacao: HttpResponse<TipoTransacao>) => {
          if (tipoTransacao.body) {
            return of(tipoTransacao.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoTransacao());
  }
}
