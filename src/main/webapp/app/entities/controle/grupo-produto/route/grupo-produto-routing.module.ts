import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GrupoProdutoComponent } from '../list/grupo-produto.component';
import { GrupoProdutoDetailComponent } from '../detail/grupo-produto-detail.component';
import { GrupoProdutoUpdateComponent } from '../update/grupo-produto-update.component';
import { GrupoProdutoRoutingResolveService } from './grupo-produto-routing-resolve.service';

const grupoProdutoRoute: Routes = [
  {
    path: '',
    component: GrupoProdutoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GrupoProdutoDetailComponent,
    resolve: {
      grupoProduto: GrupoProdutoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GrupoProdutoUpdateComponent,
    resolve: {
      grupoProduto: GrupoProdutoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GrupoProdutoUpdateComponent,
    resolve: {
      grupoProduto: GrupoProdutoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(grupoProdutoRoute)],
  exports: [RouterModule],
})
export class GrupoProdutoRoutingModule {}
