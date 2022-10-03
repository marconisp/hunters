import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FotoSaidaEstoqueComponent } from '../list/foto-saida-estoque.component';
import { FotoSaidaEstoqueDetailComponent } from '../detail/foto-saida-estoque-detail.component';
import { FotoSaidaEstoqueUpdateComponent } from '../update/foto-saida-estoque-update.component';
import { FotoSaidaEstoqueRoutingResolveService } from './foto-saida-estoque-routing-resolve.service';

const fotoSaidaEstoqueRoute: Routes = [
  {
    path: '',
    component: FotoSaidaEstoqueComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FotoSaidaEstoqueDetailComponent,
    resolve: {
      fotoSaidaEstoque: FotoSaidaEstoqueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FotoSaidaEstoqueUpdateComponent,
    resolve: {
      fotoSaidaEstoque: FotoSaidaEstoqueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FotoSaidaEstoqueUpdateComponent,
    resolve: {
      fotoSaidaEstoque: FotoSaidaEstoqueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fotoSaidaEstoqueRoute)],
  exports: [RouterModule],
})
export class FotoSaidaEstoqueRoutingModule {}
