import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFotoEntradaEstoque, FotoEntradaEstoque } from '../foto-entrada-estoque.model';
import { FotoEntradaEstoqueService } from '../service/foto-entrada-estoque.service';

@Injectable({ providedIn: 'root' })
export class FotoEntradaEstoqueRoutingResolveService implements Resolve<IFotoEntradaEstoque> {
  constructor(protected service: FotoEntradaEstoqueService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFotoEntradaEstoque> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fotoEntradaEstoque: HttpResponse<FotoEntradaEstoque>) => {
          if (fotoEntradaEstoque.body) {
            return of(fotoEntradaEstoque.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FotoEntradaEstoque());
  }
}
