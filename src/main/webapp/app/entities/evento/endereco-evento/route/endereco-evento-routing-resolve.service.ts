import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEnderecoEvento, EnderecoEvento } from '../endereco-evento.model';
import { EnderecoEventoService } from '../service/endereco-evento.service';

@Injectable({ providedIn: 'root' })
export class EnderecoEventoRoutingResolveService implements Resolve<IEnderecoEvento> {
  constructor(protected service: EnderecoEventoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEnderecoEvento> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((enderecoEvento: HttpResponse<EnderecoEvento>) => {
          if (enderecoEvento.body) {
            return of(enderecoEvento.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EnderecoEvento());
  }
}
