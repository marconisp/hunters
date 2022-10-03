import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of } from 'rxjs';

import { IReceber, Receber } from '../../../controle/receber/receber.model';

@Injectable({ providedIn: 'root' })
export class FotoReceberRoutingResolveReceberService implements Resolve<IReceber> {
  constructor(protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReceber> | Observable<never> {
    const id = route.params['idReceber'];
    if (id) {
      const receber = new Receber();
      receber.id = id;
      return of(receber);
    }
    return of(new Receber());
  }
}
