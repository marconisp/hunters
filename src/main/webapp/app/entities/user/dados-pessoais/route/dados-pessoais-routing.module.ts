import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DadosPessoaisComponent } from '../list/dados-pessoais.component';
import { DadosPessoaisDetailComponent } from '../detail/dados-pessoais-detail.component';
import { DadosPessoaisUpdateComponent } from '../update/dados-pessoais-update.component';
import { DadosPessoaisRoutingResolveService } from './dados-pessoais-routing-resolve.service';

const dadosPessoaisRoute: Routes = [
  {
    path: '',
    component: DadosPessoaisComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DadosPessoaisDetailComponent,
    resolve: {
      dadosPessoais: DadosPessoaisRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DadosPessoaisUpdateComponent,
    resolve: {
      dadosPessoais: DadosPessoaisRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DadosPessoaisUpdateComponent,
    resolve: {
      dadosPessoais: DadosPessoaisRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dadosPessoaisRoute)],
  exports: [RouterModule],
})
export class DadosPessoaisRoutingModule {}
