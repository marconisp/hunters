import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ReceberComponent } from '../list/receber.component';
import { ReceberDetailComponent } from '../detail/receber-detail.component';
import { ReceberUpdateComponent } from '../update/receber-update.component';
import { ReceberRoutingResolveService } from './receber-routing-resolve.service';
import { ReceberReportComponent } from '../report/receber-report.component';

const receberRoute: Routes = [
  {
    path: '',
    component: ReceberComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ReceberDetailComponent,
    resolve: {
      receber: ReceberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ReceberUpdateComponent,
    resolve: {
      receber: ReceberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ReceberUpdateComponent,
    resolve: {
      receber: ReceberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'report',
    component: ReceberReportComponent,
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(receberRoute)],
  exports: [RouterModule],
})
export class ReceberRoutingModule {}
