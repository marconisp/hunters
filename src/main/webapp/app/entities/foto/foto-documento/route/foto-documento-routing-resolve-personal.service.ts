import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of } from 'rxjs';

import { IDocumento, Documento } from 'app/entities/user/documento/documento.model';

@Injectable({ providedIn: 'root' })
export class FotoDocumentoRoutingResolvePessoaService implements Resolve<IDocumento> {
  constructor(protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDocumento> | Observable<never> {
    const id = route.params['idDocumento'];
    if (id) {
      const documento = new Documento();
      documento.id = id;
      return of(documento);
    }
    return of(new Documento());
  }
}
