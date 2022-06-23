import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDadosPessoais, DadosPessoais } from '../dados-pessoais.model';
import { DadosPessoaisService } from '../service/dados-pessoais.service';

@Injectable({ providedIn: 'root' })
export class DadosPessoaisRoutingResolveService implements Resolve<IDadosPessoais> {
  constructor(protected service: DadosPessoaisService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDadosPessoais> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dadosPessoais: HttpResponse<DadosPessoais>) => {
          if (dadosPessoais.body) {
            return of(dadosPessoais.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DadosPessoais());
  }
}
