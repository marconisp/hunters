import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IColaborador, Colaborador } from '../colaborador.model';
import { ColaboradorService } from '../service/colaborador.service';

@Injectable({ providedIn: 'root' })
export class ColaboradorRoutingResolveService implements Resolve<IColaborador> {
  constructor(protected service: ColaboradorService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IColaborador> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((colaborador: HttpResponse<Colaborador>) => {
          if (colaborador.body) {
            return of(colaborador.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Colaborador());
  }
}
