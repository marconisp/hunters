import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FotoIconComponent } from '../list/foto-icon.component';
import { FotoIconDetailComponent } from '../detail/foto-icon-detail.component';
import { FotoIconUpdateComponent } from '../update/foto-icon-update.component';
import { FotoIconRoutingResolveService } from './foto-icon-routing-resolve.service';

const fotoIconRoute: Routes = [
  {
    path: '',
    component: FotoIconComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FotoIconDetailComponent,
    resolve: {
      fotoIcon: FotoIconRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FotoIconUpdateComponent,
    resolve: {
      fotoIcon: FotoIconRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FotoIconUpdateComponent,
    resolve: {
      fotoIcon: FotoIconRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fotoIconRoute)],
  exports: [RouterModule],
})
export class FotoIconRoutingModule {}
