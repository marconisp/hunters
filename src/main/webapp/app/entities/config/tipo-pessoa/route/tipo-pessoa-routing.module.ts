import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TipoPessoaComponent } from '../list/tipo-pessoa.component';
import { TipoPessoaDetailComponent } from '../detail/tipo-pessoa-detail.component';
import { TipoPessoaUpdateComponent } from '../update/tipo-pessoa-update.component';
import { TipoPessoaRoutingResolveService } from './tipo-pessoa-routing-resolve.service';

const tipoPessoaRoute: Routes = [
  {
    path: '',
    component: TipoPessoaComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TipoPessoaDetailComponent,
    resolve: {
      tipoPessoa: TipoPessoaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TipoPessoaUpdateComponent,
    resolve: {
      tipoPessoa: TipoPessoaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TipoPessoaUpdateComponent,
    resolve: {
      tipoPessoa: TipoPessoaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tipoPessoaRoute)],
  exports: [RouterModule],
})
export class TipoPessoaRoutingModule {}
