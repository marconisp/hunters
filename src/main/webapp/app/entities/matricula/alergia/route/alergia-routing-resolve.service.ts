import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAlergia, Alergia } from '../alergia.model';
import { AlergiaService } from '../service/alergia.service';

@Injectable({ providedIn: 'root' })
export class AlergiaRoutingResolveService implements Resolve<IAlergia> {
  constructor(protected service: AlergiaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAlergia> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((alergia: HttpResponse<Alergia>) => {
          if (alergia.body) {
            return of(alergia.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Alergia());
  }
}
