import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMensagem, Mensagem } from '../mensagem.model';
import { MensagemService } from '../service/mensagem.service';

@Injectable({ providedIn: 'root' })
export class MensagemRoutingResolveService implements Resolve<IMensagem> {
  constructor(protected service: MensagemService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMensagem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((mensagem: HttpResponse<Mensagem>) => {
          if (mensagem.body) {
            return of(mensagem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Mensagem());
  }
}
