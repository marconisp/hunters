import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DadosMedicoComponent } from '../list/dados-medico.component';
import { DadosMedicoDetailComponent } from '../detail/dados-medico-detail.component';
import { DadosMedicoUpdateComponent } from '../update/dados-medico-update.component';
import { DadosMedicoRoutingResolveService } from './dados-medico-routing-resolve.service';

const dadosMedicoRoute: Routes = [
  {
    path: '',
    component: DadosMedicoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DadosMedicoDetailComponent,
    resolve: {
      dadosMedico: DadosMedicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DadosMedicoUpdateComponent,
    resolve: {
      dadosMedico: DadosMedicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DadosMedicoUpdateComponent,
    resolve: {
      dadosMedico: DadosMedicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dadosMedicoRoute)],
  exports: [RouterModule],
})
export class DadosMedicoRoutingModule {}
