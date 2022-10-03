import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EntradaEstoqueComponent } from '../list/entrada-estoque.component';
import { EntradaEstoqueDetailComponent } from '../detail/entrada-estoque-detail.component';
import { EntradaEstoqueUpdateComponent } from '../update/entrada-estoque-update.component';
import { EntradaEstoqueRoutingResolveService } from './entrada-estoque-routing-resolve.service';

const entradaEstoqueRoute: Routes = [
  {
    path: '',
    component: EntradaEstoqueComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EntradaEstoqueDetailComponent,
    resolve: {
      entradaEstoque: EntradaEstoqueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EntradaEstoqueUpdateComponent,
    resolve: {
      entradaEstoque: EntradaEstoqueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EntradaEstoqueUpdateComponent,
    resolve: {
      entradaEstoque: EntradaEstoqueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(entradaEstoqueRoute)],
  exports: [RouterModule],
})
export class EntradaEstoqueRoutingModule {}
