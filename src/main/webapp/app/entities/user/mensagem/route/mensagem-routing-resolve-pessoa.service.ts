import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of } from 'rxjs';

import { IDadosPessoais, DadosPessoais } from '../../dados-pessoais/dados-pessoais.model';

@Injectable({ providedIn: 'root' })
export class MensagemRoutingResolvePessoaService implements Resolve<IDadosPessoais> {
  constructor(protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDadosPessoais> | Observable<never> {
    const id = route.params['idDadospessoais'];
    if (id) {
      const pessoais = new DadosPessoais();
      pessoais.id = id;
      return of(pessoais);
    }
    return of(new DadosPessoais());
  }
}
