import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEstoque, Estoque } from '../estoque.model';
import { EstoqueService } from '../service/estoque.service';

@Injectable({ providedIn: 'root' })
export class EstoqueRoutingResolveService implements Resolve<IEstoque> {
  constructor(protected service: EstoqueService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEstoque> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((estoque: HttpResponse<Estoque>) => {
          if (estoque.body) {
            return of(estoque.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Estoque());
  }
}
