import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDadosMedico, DadosMedico } from '../dados-medico.model';
import { DadosMedicoService } from '../service/dados-medico.service';

@Injectable({ providedIn: 'root' })
export class DadosMedicoRoutingResolveService implements Resolve<IDadosMedico> {
  constructor(protected service: DadosMedicoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDadosMedico> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dadosMedico: HttpResponse<DadosMedico>) => {
          if (dadosMedico.body) {
            return of(dadosMedico.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DadosMedico());
  }
}
