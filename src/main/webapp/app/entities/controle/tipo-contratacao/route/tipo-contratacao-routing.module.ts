import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TipoContratacaoComponent } from '../list/tipo-contratacao.component';
import { TipoContratacaoDetailComponent } from '../detail/tipo-contratacao-detail.component';
import { TipoContratacaoUpdateComponent } from '../update/tipo-contratacao-update.component';
import { TipoContratacaoRoutingResolveService } from './tipo-contratacao-routing-resolve.service';

const tipoContratacaoRoute: Routes = [
  {
    path: '',
    component: TipoContratacaoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TipoContratacaoDetailComponent,
    resolve: {
      tipoContratacao: TipoContratacaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TipoContratacaoUpdateComponent,
    resolve: {
      tipoContratacao: TipoContratacaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TipoContratacaoUpdateComponent,
    resolve: {
      tipoContratacao: TipoContratacaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tipoContratacaoRoute)],
  exports: [RouterModule],
})
export class TipoContratacaoRoutingModule {}
