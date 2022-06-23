import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFotoDocumento, FotoDocumento } from '../foto-documento.model';
import { FotoDocumentoService } from '../service/foto-documento.service';

@Injectable({ providedIn: 'root' })
export class FotoDocumentoRoutingResolveService implements Resolve<IFotoDocumento> {
  constructor(protected service: FotoDocumentoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFotoDocumento> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fotoDocumento: HttpResponse<FotoDocumento>) => {
          if (fotoDocumento.body) {
            return of(fotoDocumento.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FotoDocumento());
  }
}
