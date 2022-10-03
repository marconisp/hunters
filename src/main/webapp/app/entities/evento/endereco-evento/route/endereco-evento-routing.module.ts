import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EnderecoEventoComponent } from '../list/endereco-evento.component';
import { EnderecoEventoDetailComponent } from '../detail/endereco-evento-detail.component';
import { EnderecoEventoUpdateComponent } from '../update/endereco-evento-update.component';
import { EnderecoEventoRoutingResolveService } from './endereco-evento-routing-resolve.service';

const enderecoEventoRoute: Routes = [
  {
    path: '',
    component: EnderecoEventoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EnderecoEventoDetailComponent,
    resolve: {
      enderecoEvento: EnderecoEventoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EnderecoEventoUpdateComponent,
    resolve: {
      enderecoEvento: EnderecoEventoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EnderecoEventoUpdateComponent,
    resolve: {
      enderecoEvento: EnderecoEventoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(enderecoEventoRoute)],
  exports: [RouterModule],
})
export class EnderecoEventoRoutingModule {}
