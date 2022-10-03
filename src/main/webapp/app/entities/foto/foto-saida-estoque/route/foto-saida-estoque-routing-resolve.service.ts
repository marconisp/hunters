import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFotoSaidaEstoque, FotoSaidaEstoque } from '../foto-saida-estoque.model';
import { FotoSaidaEstoqueService } from '../service/foto-saida-estoque.service';

@Injectable({ providedIn: 'root' })
export class FotoSaidaEstoqueRoutingResolveService implements Resolve<IFotoSaidaEstoque> {
  constructor(protected service: FotoSaidaEstoqueService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFotoSaidaEstoque> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fotoSaidaEstoque: HttpResponse<FotoSaidaEstoque>) => {
          if (fotoSaidaEstoque.body) {
            return of(fotoSaidaEstoque.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FotoSaidaEstoque());
  }
}
