import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IItemMateria, ItemMateria } from '../item-materia.model';
import { ItemMateriaService } from '../service/item-materia.service';

@Injectable({ providedIn: 'root' })
export class ItemMateriaRoutingResolveService implements Resolve<IItemMateria> {
  constructor(protected service: ItemMateriaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IItemMateria> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((itemMateria: HttpResponse<ItemMateria>) => {
          if (itemMateria.body) {
            return of(itemMateria.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ItemMateria());
  }
}
