import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EnderecoComponent } from '../list/endereco.component';
import { EnderecoDetailComponent } from '../detail/endereco-detail.component';
import { EnderecoUpdateComponent } from '../update/endereco-update.component';
import { EnderecoRoutingResolveService } from './endereco-routing-resolve.service';
import { EnderecoRoutingResolvePessoaService } from './endereco-routing-resolve-pessoa.service';

const enderecoRoute: Routes = [
  {
    path: '',
    component: EnderecoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'dadospessoais/:idDadospessoais',
    component: EnderecoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    resolve: {
      dadosPessoais: EnderecoRoutingResolvePessoaService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EnderecoDetailComponent,
    resolve: {
      endereco: EnderecoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EnderecoUpdateComponent,
    resolve: {
      endereco: EnderecoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new/dadospessoais/:idDadospessoais',
    component: EnderecoUpdateComponent,
    resolve: {
      endereco: EnderecoRoutingResolveService,
      dadosPessoais: EnderecoRoutingResolvePessoaService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EnderecoUpdateComponent,
    resolve: {
      endereco: EnderecoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(enderecoRoute)],
  exports: [RouterModule],
})
export class EnderecoRoutingModule {}
