import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITipoContratacao, TipoContratacao } from '../tipo-contratacao.model';
import { TipoContratacaoService } from '../service/tipo-contratacao.service';

@Injectable({ providedIn: 'root' })
export class TipoContratacaoRoutingResolveService implements Resolve<ITipoContratacao> {
  constructor(protected service: TipoContratacaoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoContratacao> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tipoContratacao: HttpResponse<TipoContratacao>) => {
          if (tipoContratacao.body) {
            return of(tipoContratacao.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoContratacao());
  }
}
