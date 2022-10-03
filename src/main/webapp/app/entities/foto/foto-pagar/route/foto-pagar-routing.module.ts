import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FotoPagarComponent } from '../list/foto-pagar.component';
import { FotoPagarDetailComponent } from '../detail/foto-pagar-detail.component';
import { FotoPagarUpdateComponent } from '../update/foto-pagar-update.component';
import { FotoPagarRoutingResolveService } from './foto-pagar-routing-resolve.service';
import { FotoPagarRoutingResolvePagarService } from './foto-pagar-routing-resolve.pagar.service';

const fotoPagarRoute: Routes = [
  {
    path: '',
    component: FotoPagarComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'pagar/:idPagar',
    component: FotoPagarComponent,
    data: {
      defaultSort: 'id,asc',
    },
    resolve: {
      pagar: FotoPagarRoutingResolvePagarService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FotoPagarDetailComponent,
    resolve: {
      fotoPagar: FotoPagarRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FotoPagarUpdateComponent,
    resolve: {
      fotoPagar: FotoPagarRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new/pagar/:idPagar',
    component: FotoPagarUpdateComponent,
    resolve: {
      fotoPagar: FotoPagarRoutingResolveService,
      pagar: FotoPagarRoutingResolvePagarService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FotoPagarUpdateComponent,
    resolve: {
      fotoPagar: FotoPagarRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fotoPagarRoute)],
  exports: [RouterModule],
})
export class FotoPagarRoutingModule {}
