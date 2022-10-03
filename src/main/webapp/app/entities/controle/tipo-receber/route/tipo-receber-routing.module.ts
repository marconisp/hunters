import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TipoReceberComponent } from '../list/tipo-receber.component';
import { TipoReceberDetailComponent } from '../detail/tipo-receber-detail.component';
import { TipoReceberUpdateComponent } from '../update/tipo-receber-update.component';
import { TipoReceberRoutingResolveService } from './tipo-receber-routing-resolve.service';

const tipoReceberRoute: Routes = [
  {
    path: '',
    component: TipoReceberComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TipoReceberDetailComponent,
    resolve: {
      tipoReceber: TipoReceberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TipoReceberUpdateComponent,
    resolve: {
      tipoReceber: TipoReceberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TipoReceberUpdateComponent,
    resolve: {
      tipoReceber: TipoReceberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tipoReceberRoute)],
  exports: [RouterModule],
})
export class TipoReceberRoutingModule {}
