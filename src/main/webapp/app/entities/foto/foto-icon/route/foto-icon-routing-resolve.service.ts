import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFotoIcon, FotoIcon } from '../foto-icon.model';
import { FotoIconService } from '../service/foto-icon.service';

@Injectable({ providedIn: 'root' })
export class FotoIconRoutingResolveService implements Resolve<IFotoIcon> {
  constructor(protected service: FotoIconService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFotoIcon> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fotoIcon: HttpResponse<FotoIcon>) => {
          if (fotoIcon.body) {
            return of(fotoIcon.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FotoIcon());
  }
}
