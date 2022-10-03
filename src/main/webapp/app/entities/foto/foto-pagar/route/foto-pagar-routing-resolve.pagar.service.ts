import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of } from 'rxjs';

import { IPagar, Pagar } from '../../../controle/pagar/pagar.model';

@Injectable({ providedIn: 'root' })
export class FotoPagarRoutingResolvePagarService implements Resolve<IPagar> {
  constructor(protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPagar> | Observable<never> {
    const id = route.params['idPagar'];
    if (id) {
      const documento = new Pagar();
      documento.id = id;
      return of(documento);
    }
    return of(new Pagar());
  }
}
