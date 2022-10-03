import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISubGrupoProduto, SubGrupoProduto } from '../sub-grupo-produto.model';
import { SubGrupoProdutoService } from '../service/sub-grupo-produto.service';

@Injectable({ providedIn: 'root' })
export class SubGrupoProdutoRoutingResolveService implements Resolve<ISubGrupoProduto> {
  constructor(protected service: SubGrupoProdutoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISubGrupoProduto> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((subGrupoProduto: HttpResponse<SubGrupoProduto>) => {
          if (subGrupoProduto.body) {
            return of(subGrupoProduto.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SubGrupoProduto());
  }
}
