import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGrupoProduto, GrupoProduto } from '../grupo-produto.model';
import { GrupoProdutoService } from '../service/grupo-produto.service';

@Injectable({ providedIn: 'root' })
export class GrupoProdutoRoutingResolveService implements Resolve<IGrupoProduto> {
  constructor(protected service: GrupoProdutoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGrupoProduto> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((grupoProduto: HttpResponse<GrupoProduto>) => {
          if (grupoProduto.body) {
            return of(grupoProduto.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GrupoProduto());
  }
}
