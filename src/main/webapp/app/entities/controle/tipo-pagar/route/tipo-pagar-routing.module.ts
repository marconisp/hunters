import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TipoPagarComponent } from '../list/tipo-pagar.component';
import { TipoPagarDetailComponent } from '../detail/tipo-pagar-detail.component';
import { TipoPagarUpdateComponent } from '../update/tipo-pagar-update.component';
import { TipoPagarRoutingResolveService } from './tipo-pagar-routing-resolve.service';

const tipoPagarRoute: Routes = [
  {
    path: '',
    component: TipoPagarComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TipoPagarDetailComponent,
    resolve: {
      tipoPagar: TipoPagarRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TipoPagarUpdateComponent,
    resolve: {
      tipoPagar: TipoPagarRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TipoPagarUpdateComponent,
    resolve: {
      tipoPagar: TipoPagarRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tipoPagarRoute)],
  exports: [RouterModule],
})
export class TipoPagarRoutingModule {}
