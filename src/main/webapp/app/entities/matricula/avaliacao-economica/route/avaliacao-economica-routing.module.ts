import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AvaliacaoEconomicaComponent } from '../list/avaliacao-economica.component';
import { AvaliacaoEconomicaDetailComponent } from '../detail/avaliacao-economica-detail.component';
import { AvaliacaoEconomicaUpdateComponent } from '../update/avaliacao-economica-update.component';
import { AvaliacaoEconomicaRoutingResolveService } from './avaliacao-economica-routing-resolve.service';

const avaliacaoEconomicaRoute: Routes = [
  {
    path: '',
    component: AvaliacaoEconomicaComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AvaliacaoEconomicaDetailComponent,
    resolve: {
      avaliacaoEconomica: AvaliacaoEconomicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AvaliacaoEconomicaUpdateComponent,
    resolve: {
      avaliacaoEconomica: AvaliacaoEconomicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AvaliacaoEconomicaUpdateComponent,
    resolve: {
      avaliacaoEconomica: AvaliacaoEconomicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(avaliacaoEconomicaRoute)],
  exports: [RouterModule],
})
export class AvaliacaoEconomicaRoutingModule {}
