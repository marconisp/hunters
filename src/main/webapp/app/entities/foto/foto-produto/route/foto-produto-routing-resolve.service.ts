import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFotoProduto, FotoProduto } from '../foto-produto.model';
import { FotoProdutoService } from '../service/foto-produto.service';

@Injectable({ providedIn: 'root' })
export class FotoProdutoRoutingResolveService implements Resolve<IFotoProduto> {
  constructor(protected service: FotoProdutoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFotoProduto> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fotoProduto: HttpResponse<FotoProduto>) => {
          if (fotoProduto.body) {
            return of(fotoProduto.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FotoProduto());
  }
}
