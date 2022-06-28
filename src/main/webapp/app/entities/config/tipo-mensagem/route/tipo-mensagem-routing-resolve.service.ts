import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITipoMensagem, TipoMensagem } from '../tipo-mensagem.model';
import { TipoMensagemService } from '../service/tipo-mensagem.service';

@Injectable({ providedIn: 'root' })
export class TipoMensagemRoutingResolveService implements Resolve<ITipoMensagem> {
  constructor(protected service: TipoMensagemService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoMensagem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tipoMensagem: HttpResponse<TipoMensagem>) => {
          if (tipoMensagem.body) {
            return of(tipoMensagem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoMensagem());
  }
}
