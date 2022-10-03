import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAcompanhamentoAluno, AcompanhamentoAluno } from '../acompanhamento-aluno.model';
import { AcompanhamentoAlunoService } from '../service/acompanhamento-aluno.service';

@Injectable({ providedIn: 'root' })
export class AcompanhamentoAlunoRoutingResolveService implements Resolve<IAcompanhamentoAluno> {
  constructor(protected service: AcompanhamentoAlunoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAcompanhamentoAluno> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((acompanhamentoAluno: HttpResponse<AcompanhamentoAluno>) => {
          if (acompanhamentoAluno.body) {
            return of(acompanhamentoAluno.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AcompanhamentoAluno());
  }
}
