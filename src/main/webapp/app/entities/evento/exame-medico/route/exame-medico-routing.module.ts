import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ExameMedicoComponent } from '../list/exame-medico.component';
import { ExameMedicoDetailComponent } from '../detail/exame-medico-detail.component';
import { ExameMedicoUpdateComponent } from '../update/exame-medico-update.component';
import { ExameMedicoRoutingResolveService } from './exame-medico-routing-resolve.service';

const exameMedicoRoute: Routes = [
  {
    path: '',
    component: ExameMedicoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ExameMedicoDetailComponent,
    resolve: {
      exameMedico: ExameMedicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ExameMedicoUpdateComponent,
    resolve: {
      exameMedico: ExameMedicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ExameMedicoUpdateComponent,
    resolve: {
      exameMedico: ExameMedicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(exameMedicoRoute)],
  exports: [RouterModule],
})
export class ExameMedicoRoutingModule {}
