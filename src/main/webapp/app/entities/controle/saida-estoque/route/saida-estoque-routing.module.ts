import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SaidaEstoqueComponent } from '../list/saida-estoque.component';
import { SaidaEstoqueDetailComponent } from '../detail/saida-estoque-detail.component';
import { SaidaEstoqueUpdateComponent } from '../update/saida-estoque-update.component';
import { SaidaEstoqueRoutingResolveService } from './saida-estoque-routing-resolve.service';

const saidaEstoqueRoute: Routes = [
  {
    path: '',
    component: SaidaEstoqueComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SaidaEstoqueDetailComponent,
    resolve: {
      saidaEstoque: SaidaEstoqueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SaidaEstoqueUpdateComponent,
    resolve: {
      saidaEstoque: SaidaEstoqueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SaidaEstoqueUpdateComponent,
    resolve: {
      saidaEstoque: SaidaEstoqueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(saidaEstoqueRoute)],
  exports: [RouterModule],
})
export class SaidaEstoqueRoutingModule {}
