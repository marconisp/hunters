import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEntradaEstoque, EntradaEstoque } from '../entrada-estoque.model';
import { EntradaEstoqueService } from '../service/entrada-estoque.service';

@Injectable({ providedIn: 'root' })
export class EntradaEstoqueRoutingResolveService implements Resolve<IEntradaEstoque> {
  constructor(protected service: EntradaEstoqueService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEntradaEstoque> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((entradaEstoque: HttpResponse<EntradaEstoque>) => {
          if (entradaEstoque.body) {
            return of(entradaEstoque.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EntradaEstoque());
  }
}
