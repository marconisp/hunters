import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FotoProdutoComponent } from '../list/foto-produto.component';
import { FotoProdutoDetailComponent } from '../detail/foto-produto-detail.component';
import { FotoProdutoUpdateComponent } from '../update/foto-produto-update.component';
import { FotoProdutoRoutingResolveService } from './foto-produto-routing-resolve.service';

const fotoProdutoRoute: Routes = [
  {
    path: '',
    component: FotoProdutoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FotoProdutoDetailComponent,
    resolve: {
      fotoProduto: FotoProdutoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FotoProdutoUpdateComponent,
    resolve: {
      fotoProduto: FotoProdutoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FotoProdutoUpdateComponent,
    resolve: {
      fotoProduto: FotoProdutoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fotoProdutoRoute)],
  exports: [RouterModule],
})
export class FotoProdutoRoutingModule {}
