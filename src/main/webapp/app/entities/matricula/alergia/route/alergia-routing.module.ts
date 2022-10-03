import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AlergiaComponent } from '../list/alergia.component';
import { AlergiaDetailComponent } from '../detail/alergia-detail.component';
import { AlergiaUpdateComponent } from '../update/alergia-update.component';
import { AlergiaRoutingResolveService } from './alergia-routing-resolve.service';

const alergiaRoute: Routes = [
  {
    path: '',
    component: AlergiaComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AlergiaDetailComponent,
    resolve: {
      alergia: AlergiaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AlergiaUpdateComponent,
    resolve: {
      alergia: AlergiaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AlergiaUpdateComponent,
    resolve: {
      alergia: AlergiaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(alergiaRoute)],
  exports: [RouterModule],
})
export class AlergiaRoutingModule {}
