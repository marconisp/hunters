import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PagarParaComponent } from '../list/pagar-para.component';
import { PagarParaDetailComponent } from '../detail/pagar-para-detail.component';
import { PagarParaUpdateComponent } from '../update/pagar-para-update.component';
import { PagarParaRoutingResolveService } from './pagar-para-routing-resolve.service';

const pagarParaRoute: Routes = [
  {
    path: '',
    component: PagarParaComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PagarParaDetailComponent,
    resolve: {
      pagarPara: PagarParaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PagarParaUpdateComponent,
    resolve: {
      pagarPara: PagarParaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PagarParaUpdateComponent,
    resolve: {
      pagarPara: PagarParaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pagarParaRoute)],
  exports: [RouterModule],
})
export class PagarParaRoutingModule {}
