import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDiaSemana, DiaSemana } from '../dia-semana.model';
import { DiaSemanaService } from '../service/dia-semana.service';

@Injectable({ providedIn: 'root' })
export class DiaSemanaRoutingResolveService implements Resolve<IDiaSemana> {
  constructor(protected service: DiaSemanaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDiaSemana> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((diaSemana: HttpResponse<DiaSemana>) => {
          if (diaSemana.body) {
            return of(diaSemana.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DiaSemana());
  }
}
