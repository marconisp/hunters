import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEstadoCivil, EstadoCivil } from '../estado-civil.model';
import { EstadoCivilService } from '../service/estado-civil.service';

@Injectable({ providedIn: 'root' })
export class EstadoCivilRoutingResolveService implements Resolve<IEstadoCivil> {
  constructor(protected service: EstadoCivilService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEstadoCivil> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((estadoCivil: HttpResponse<EstadoCivil>) => {
          if (estadoCivil.body) {
            return of(estadoCivil.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EstadoCivil());
  }
}
