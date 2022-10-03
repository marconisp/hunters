import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FotoExameMedicoComponent } from '../list/foto-exame-medico.component';
import { FotoExameMedicoDetailComponent } from '../detail/foto-exame-medico-detail.component';
import { FotoExameMedicoUpdateComponent } from '../update/foto-exame-medico-update.component';
import { FotoExameMedicoRoutingResolveService } from './foto-exame-medico-routing-resolve.service';

const fotoExameMedicoRoute: Routes = [
  {
    path: '',
    component: FotoExameMedicoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FotoExameMedicoDetailComponent,
    resolve: {
      fotoExameMedico: FotoExameMedicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FotoExameMedicoUpdateComponent,
    resolve: {
      fotoExameMedico: FotoExameMedicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FotoExameMedicoUpdateComponent,
    resolve: {
      fotoExameMedico: FotoExameMedicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fotoExameMedicoRoute)],
  exports: [RouterModule],
})
export class FotoExameMedicoRoutingModule {}
