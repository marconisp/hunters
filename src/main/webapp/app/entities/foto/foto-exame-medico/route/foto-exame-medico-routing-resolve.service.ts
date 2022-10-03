import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFotoExameMedico, FotoExameMedico } from '../foto-exame-medico.model';
import { FotoExameMedicoService } from '../service/foto-exame-medico.service';

@Injectable({ providedIn: 'root' })
export class FotoExameMedicoRoutingResolveService implements Resolve<IFotoExameMedico> {
  constructor(protected service: FotoExameMedicoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFotoExameMedico> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fotoExameMedico: HttpResponse<FotoExameMedico>) => {
          if (fotoExameMedico.body) {
            return of(fotoExameMedico.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FotoExameMedico());
  }
}
