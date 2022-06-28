import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITipoPessoa, TipoPessoa } from '../tipo-pessoa.model';
import { TipoPessoaService } from '../service/tipo-pessoa.service';

@Injectable({ providedIn: 'root' })
export class TipoPessoaRoutingResolveService implements Resolve<ITipoPessoa> {
  constructor(protected service: TipoPessoaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoPessoa> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tipoPessoa: HttpResponse<TipoPessoa>) => {
          if (tipoPessoa.body) {
            return of(tipoPessoa.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoPessoa());
  }
}
