import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAgendaColaborador, AgendaColaborador } from '../agenda-colaborador.model';
import { AgendaColaboradorService } from '../service/agenda-colaborador.service';

@Injectable({ providedIn: 'root' })
export class AgendaColaboradorRoutingResolveService implements Resolve<IAgendaColaborador> {
  constructor(protected service: AgendaColaboradorService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAgendaColaborador> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((agendaColaborador: HttpResponse<AgendaColaborador>) => {
          if (agendaColaborador.body) {
            return of(agendaColaborador.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AgendaColaborador());
  }
}
