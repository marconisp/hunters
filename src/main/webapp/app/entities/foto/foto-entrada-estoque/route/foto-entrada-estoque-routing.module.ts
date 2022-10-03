import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FotoEntradaEstoqueComponent } from '../list/foto-entrada-estoque.component';
import { FotoEntradaEstoqueDetailComponent } from '../detail/foto-entrada-estoque-detail.component';
import { FotoEntradaEstoqueUpdateComponent } from '../update/foto-entrada-estoque-update.component';
import { FotoEntradaEstoqueRoutingResolveService } from './foto-entrada-estoque-routing-resolve.service';

const fotoEntradaEstoqueRoute: Routes = [
  {
    path: '',
    component: FotoEntradaEstoqueComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FotoEntradaEstoqueDetailComponent,
    resolve: {
      fotoEntradaEstoque: FotoEntradaEstoqueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FotoEntradaEstoqueUpdateComponent,
    resolve: {
      fotoEntradaEstoque: FotoEntradaEstoqueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FotoEntradaEstoqueUpdateComponent,
    resolve: {
      fotoEntradaEstoque: FotoEntradaEstoqueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fotoEntradaEstoqueRoute)],
  exports: [RouterModule],
})
export class FotoEntradaEstoqueRoutingModule {}
