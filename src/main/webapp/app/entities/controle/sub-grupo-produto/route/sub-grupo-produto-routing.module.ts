import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SubGrupoProdutoComponent } from '../list/sub-grupo-produto.component';
import { SubGrupoProdutoDetailComponent } from '../detail/sub-grupo-produto-detail.component';
import { SubGrupoProdutoUpdateComponent } from '../update/sub-grupo-produto-update.component';
import { SubGrupoProdutoRoutingResolveService } from './sub-grupo-produto-routing-resolve.service';

const subGrupoProdutoRoute: Routes = [
  {
    path: '',
    component: SubGrupoProdutoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SubGrupoProdutoDetailComponent,
    resolve: {
      subGrupoProduto: SubGrupoProdutoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SubGrupoProdutoUpdateComponent,
    resolve: {
      subGrupoProduto: SubGrupoProdutoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SubGrupoProdutoUpdateComponent,
    resolve: {
      subGrupoProduto: SubGrupoProdutoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(subGrupoProdutoRoute)],
  exports: [RouterModule],
})
export class SubGrupoProdutoRoutingModule {}
