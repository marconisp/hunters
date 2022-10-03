import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ColaboradorComponent } from '../list/colaborador.component';
import { ColaboradorDetailComponent } from '../detail/colaborador-detail.component';
import { ColaboradorUpdateComponent } from '../update/colaborador-update.component';
import { ColaboradorRoutingResolveService } from './colaborador-routing-resolve.service';

const colaboradorRoute: Routes = [
  {
    path: '',
    component: ColaboradorComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ColaboradorDetailComponent,
    resolve: {
      colaborador: ColaboradorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ColaboradorUpdateComponent,
    resolve: {
      colaborador: ColaboradorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ColaboradorUpdateComponent,
    resolve: {
      colaborador: ColaboradorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(colaboradorRoute)],
  exports: [RouterModule],
})
export class ColaboradorRoutingModule {}
