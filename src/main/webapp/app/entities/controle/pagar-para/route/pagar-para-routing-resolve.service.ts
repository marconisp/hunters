import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPagarPara, PagarPara } from '../pagar-para.model';
import { PagarParaService } from '../service/pagar-para.service';

@Injectable({ providedIn: 'root' })
export class PagarParaRoutingResolveService implements Resolve<IPagarPara> {
  constructor(protected service: PagarParaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPagarPara> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pagarPara: HttpResponse<PagarPara>) => {
          if (pagarPara.body) {
            return of(pagarPara.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PagarPara());
  }
}
