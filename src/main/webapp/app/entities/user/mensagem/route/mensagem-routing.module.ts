import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MensagemComponent } from '../list/mensagem.component';
import { MensagemDetailComponent } from '../detail/mensagem-detail.component';
import { MensagemUpdateComponent } from '../update/mensagem-update.component';
import { MensagemRoutingResolveService } from './mensagem-routing-resolve.service';
import { MensagemRoutingResolvePessoaService } from './mensagem-routing-resolve-pessoa.service';

const mensagemRoute: Routes = [
  {
    path: '',
    component: MensagemComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'dadospessoais/:idDadospessoais',
    component: MensagemComponent,
    data: {
      defaultSort: 'id,asc',
    },
    resolve: {
      dadosPessoais: MensagemRoutingResolvePessoaService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MensagemDetailComponent,
    resolve: {
      mensagem: MensagemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MensagemUpdateComponent,
    resolve: {
      mensagem: MensagemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new/dadospessoais/:idDadospessoais',
    component: MensagemUpdateComponent,
    resolve: {
      mensagem: MensagemRoutingResolveService,
      dadosPessoais: MensagemRoutingResolvePessoaService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MensagemUpdateComponent,
    resolve: {
      mensagem: MensagemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(mensagemRoute)],
  exports: [RouterModule],
})
export class MensagemRoutingModule {}
