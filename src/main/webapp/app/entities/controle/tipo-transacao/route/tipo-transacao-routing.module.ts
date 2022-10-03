import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TipoTransacaoComponent } from '../list/tipo-transacao.component';
import { TipoTransacaoDetailComponent } from '../detail/tipo-transacao-detail.component';
import { TipoTransacaoUpdateComponent } from '../update/tipo-transacao-update.component';
import { TipoTransacaoRoutingResolveService } from './tipo-transacao-routing-resolve.service';

const tipoTransacaoRoute: Routes = [
  {
    path: '',
    component: TipoTransacaoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TipoTransacaoDetailComponent,
    resolve: {
      tipoTransacao: TipoTransacaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TipoTransacaoUpdateComponent,
    resolve: {
      tipoTransacao: TipoTransacaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TipoTransacaoUpdateComponent,
    resolve: {
      tipoTransacao: TipoTransacaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tipoTransacaoRoute)],
  exports: [RouterModule],
})
export class TipoTransacaoRoutingModule {}
