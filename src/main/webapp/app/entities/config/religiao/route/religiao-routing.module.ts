import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ReligiaoComponent } from '../list/religiao.component';
import { ReligiaoDetailComponent } from '../detail/religiao-detail.component';
import { ReligiaoUpdateComponent } from '../update/religiao-update.component';
import { ReligiaoRoutingResolveService } from './religiao-routing-resolve.service';

const religiaoRoute: Routes = [
  {
    path: '',
    component: ReligiaoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ReligiaoDetailComponent,
    resolve: {
      religiao: ReligiaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ReligiaoUpdateComponent,
    resolve: {
      religiao: ReligiaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ReligiaoUpdateComponent,
    resolve: {
      religiao: ReligiaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(religiaoRoute)],
  exports: [RouterModule],
})
export class ReligiaoRoutingModule {}
