import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PeriodoDuracaoComponent } from '../list/periodo-duracao.component';
import { PeriodoDuracaoDetailComponent } from '../detail/periodo-duracao-detail.component';
import { PeriodoDuracaoUpdateComponent } from '../update/periodo-duracao-update.component';
import { PeriodoDuracaoRoutingResolveService } from './periodo-duracao-routing-resolve.service';

const periodoDuracaoRoute: Routes = [
  {
    path: '',
    component: PeriodoDuracaoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PeriodoDuracaoDetailComponent,
    resolve: {
      periodoDuracao: PeriodoDuracaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PeriodoDuracaoUpdateComponent,
    resolve: {
      periodoDuracao: PeriodoDuracaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PeriodoDuracaoUpdateComponent,
    resolve: {
      periodoDuracao: PeriodoDuracaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(periodoDuracaoRoute)],
  exports: [RouterModule],
})
export class PeriodoDuracaoRoutingModule {}
