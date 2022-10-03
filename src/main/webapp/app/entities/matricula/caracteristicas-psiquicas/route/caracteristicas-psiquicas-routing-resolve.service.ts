import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICaracteristicasPsiquicas, CaracteristicasPsiquicas } from '../caracteristicas-psiquicas.model';
import { CaracteristicasPsiquicasService } from '../service/caracteristicas-psiquicas.service';

@Injectable({ providedIn: 'root' })
export class CaracteristicasPsiquicasRoutingResolveService implements Resolve<ICaracteristicasPsiquicas> {
  constructor(protected service: CaracteristicasPsiquicasService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICaracteristicasPsiquicas> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((caracteristicasPsiquicas: HttpResponse<CaracteristicasPsiquicas>) => {
          if (caracteristicasPsiquicas.body) {
            return of(caracteristicasPsiquicas.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CaracteristicasPsiquicas());
  }
}
