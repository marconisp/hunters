import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AvisoComponent } from '../list/aviso.component';
import { AvisoDetailComponent } from '../detail/aviso-detail.component';
import { AvisoUpdateComponent } from '../update/aviso-update.component';
import { AvisoRoutingResolveService } from './aviso-routing-resolve.service';

const avisoRoute: Routes = [
  {
    path: '',
    component: AvisoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AvisoDetailComponent,
    resolve: {
      aviso: AvisoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AvisoUpdateComponent,
    resolve: {
      aviso: AvisoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AvisoUpdateComponent,
    resolve: {
      aviso: AvisoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(avisoRoute)],
  exports: [RouterModule],
})
export class AvisoRoutingModule {}
