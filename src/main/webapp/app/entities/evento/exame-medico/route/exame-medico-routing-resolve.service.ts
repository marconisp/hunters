import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IExameMedico, ExameMedico } from '../exame-medico.model';
import { ExameMedicoService } from '../service/exame-medico.service';

@Injectable({ providedIn: 'root' })
export class ExameMedicoRoutingResolveService implements Resolve<IExameMedico> {
  constructor(protected service: ExameMedicoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExameMedico> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((exameMedico: HttpResponse<ExameMedico>) => {
          if (exameMedico.body) {
            return of(exameMedico.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ExameMedico());
  }
}
