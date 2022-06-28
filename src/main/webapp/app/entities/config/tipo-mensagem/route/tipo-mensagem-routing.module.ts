import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TipoMensagemComponent } from '../list/tipo-mensagem.component';
import { TipoMensagemDetailComponent } from '../detail/tipo-mensagem-detail.component';
import { TipoMensagemUpdateComponent } from '../update/tipo-mensagem-update.component';
import { TipoMensagemRoutingResolveService } from './tipo-mensagem-routing-resolve.service';

const tipoMensagemRoute: Routes = [
  {
    path: '',
    component: TipoMensagemComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TipoMensagemDetailComponent,
    resolve: {
      tipoMensagem: TipoMensagemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TipoMensagemUpdateComponent,
    resolve: {
      tipoMensagem: TipoMensagemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TipoMensagemUpdateComponent,
    resolve: {
      tipoMensagem: TipoMensagemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tipoMensagemRoute)],
  exports: [RouterModule],
})
export class TipoMensagemRoutingModule {}
