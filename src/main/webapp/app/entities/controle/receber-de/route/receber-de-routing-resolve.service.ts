import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IReceberDe, ReceberDe } from '../receber-de.model';
import { ReceberDeService } from '../service/receber-de.service';

@Injectable({ providedIn: 'root' })
export class ReceberDeRoutingResolveService implements Resolve<IReceberDe> {
  constructor(protected service: ReceberDeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReceberDe> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((receberDe: HttpResponse<ReceberDe>) => {
          if (receberDe.body) {
            return of(receberDe.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ReceberDe());
  }
}
