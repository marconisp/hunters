import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CaracteristicasPsiquicasComponent } from '../list/caracteristicas-psiquicas.component';
import { CaracteristicasPsiquicasDetailComponent } from '../detail/caracteristicas-psiquicas-detail.component';
import { CaracteristicasPsiquicasUpdateComponent } from '../update/caracteristicas-psiquicas-update.component';
import { CaracteristicasPsiquicasRoutingResolveService } from './caracteristicas-psiquicas-routing-resolve.service';

const caracteristicasPsiquicasRoute: Routes = [
  {
    path: '',
    component: CaracteristicasPsiquicasComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CaracteristicasPsiquicasDetailComponent,
    resolve: {
      caracteristicasPsiquicas: CaracteristicasPsiquicasRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CaracteristicasPsiquicasUpdateComponent,
    resolve: {
      caracteristicasPsiquicas: CaracteristicasPsiquicasRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CaracteristicasPsiquicasUpdateComponent,
    resolve: {
      caracteristicasPsiquicas: CaracteristicasPsiquicasRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(caracteristicasPsiquicasRoute)],
  exports: [RouterModule],
})
export class CaracteristicasPsiquicasRoutingModule {}
