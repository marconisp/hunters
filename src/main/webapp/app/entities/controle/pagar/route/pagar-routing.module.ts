import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PagarComponent } from '../list/pagar.component';
import { PagarDetailComponent } from '../detail/pagar-detail.component';
import { PagarUpdateComponent } from '../update/pagar-update.component';
import { PagarRoutingResolveService } from './pagar-routing-resolve.service';

const pagarRoute: Routes = [
  {
    path: '',
    component: PagarComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PagarDetailComponent,
    resolve: {
      pagar: PagarRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PagarUpdateComponent,
    resolve: {
      pagar: PagarRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PagarUpdateComponent,
    resolve: {
      pagar: PagarRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pagarRoute)],
  exports: [RouterModule],
})
export class PagarRoutingModule {}
