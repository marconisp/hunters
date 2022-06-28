import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RacaComponent } from '../list/raca.component';
import { RacaDetailComponent } from '../detail/raca-detail.component';
import { RacaUpdateComponent } from '../update/raca-update.component';
import { RacaRoutingResolveService } from './raca-routing-resolve.service';

const racaRoute: Routes = [
  {
    path: '',
    component: RacaComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RacaDetailComponent,
    resolve: {
      raca: RacaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RacaUpdateComponent,
    resolve: {
      raca: RacaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RacaUpdateComponent,
    resolve: {
      raca: RacaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(racaRoute)],
  exports: [RouterModule],
})
export class RacaRoutingModule {}
