import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EstadoCivilComponent } from '../list/estado-civil.component';
import { EstadoCivilDetailComponent } from '../detail/estado-civil-detail.component';
import { EstadoCivilUpdateComponent } from '../update/estado-civil-update.component';
import { EstadoCivilRoutingResolveService } from './estado-civil-routing-resolve.service';

const estadoCivilRoute: Routes = [
  {
    path: '',
    component: EstadoCivilComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EstadoCivilDetailComponent,
    resolve: {
      estadoCivil: EstadoCivilRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EstadoCivilUpdateComponent,
    resolve: {
      estadoCivil: EstadoCivilRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EstadoCivilUpdateComponent,
    resolve: {
      estadoCivil: EstadoCivilRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(estadoCivilRoute)],
  exports: [RouterModule],
})
export class EstadoCivilRoutingModule {}
