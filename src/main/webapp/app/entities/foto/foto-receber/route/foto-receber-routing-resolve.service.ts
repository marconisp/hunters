import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFotoReceber, FotoReceber } from '../foto-receber.model';
import { FotoReceberService } from '../service/foto-receber.service';

@Injectable({ providedIn: 'root' })
export class FotoReceberRoutingResolveService implements Resolve<IFotoReceber> {
  constructor(protected service: FotoReceberService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFotoReceber> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fotoReceber: HttpResponse<FotoReceber>) => {
          if (fotoReceber.body) {
            return of(fotoReceber.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FotoReceber());
  }
}
