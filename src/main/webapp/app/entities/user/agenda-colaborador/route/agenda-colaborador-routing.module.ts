import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AgendaColaboradorComponent } from '../list/agenda-colaborador.component';
import { AgendaColaboradorDetailComponent } from '../detail/agenda-colaborador-detail.component';
import { AgendaColaboradorUpdateComponent } from '../update/agenda-colaborador-update.component';
import { AgendaColaboradorRoutingResolveService } from './agenda-colaborador-routing-resolve.service';

const agendaColaboradorRoute: Routes = [
  {
    path: '',
    component: AgendaColaboradorComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AgendaColaboradorDetailComponent,
    resolve: {
      agendaColaborador: AgendaColaboradorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AgendaColaboradorUpdateComponent,
    resolve: {
      agendaColaborador: AgendaColaboradorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AgendaColaboradorUpdateComponent,
    resolve: {
      agendaColaborador: AgendaColaboradorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(agendaColaboradorRoute)],
  exports: [RouterModule],
})
export class AgendaColaboradorRoutingModule {}
