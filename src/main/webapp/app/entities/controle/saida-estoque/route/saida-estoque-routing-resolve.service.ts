import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISaidaEstoque, SaidaEstoque } from '../saida-estoque.model';
import { SaidaEstoqueService } from '../service/saida-estoque.service';

@Injectable({ providedIn: 'root' })
export class SaidaEstoqueRoutingResolveService implements Resolve<ISaidaEstoque> {
  constructor(protected service: SaidaEstoqueService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISaidaEstoque> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((saidaEstoque: HttpResponse<SaidaEstoque>) => {
          if (saidaEstoque.body) {
            return of(saidaEstoque.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SaidaEstoque());
  }
}
