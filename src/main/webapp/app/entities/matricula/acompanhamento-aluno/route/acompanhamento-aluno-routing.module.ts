import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AcompanhamentoAlunoComponent } from '../list/acompanhamento-aluno.component';
import { AcompanhamentoAlunoDetailComponent } from '../detail/acompanhamento-aluno-detail.component';
import { AcompanhamentoAlunoUpdateComponent } from '../update/acompanhamento-aluno-update.component';
import { AcompanhamentoAlunoRoutingResolveService } from './acompanhamento-aluno-routing-resolve.service';

const acompanhamentoAlunoRoute: Routes = [
  {
    path: '',
    component: AcompanhamentoAlunoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AcompanhamentoAlunoDetailComponent,
    resolve: {
      acompanhamentoAluno: AcompanhamentoAlunoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AcompanhamentoAlunoUpdateComponent,
    resolve: {
      acompanhamentoAluno: AcompanhamentoAlunoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AcompanhamentoAlunoUpdateComponent,
    resolve: {
      acompanhamentoAluno: AcompanhamentoAlunoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(acompanhamentoAlunoRoute)],
  exports: [RouterModule],
})
export class AcompanhamentoAlunoRoutingModule {}
