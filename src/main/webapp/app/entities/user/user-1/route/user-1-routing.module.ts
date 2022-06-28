import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { User1Component } from '../list/user-1.component';
import { User1DetailComponent } from '../detail/user-1-detail.component';
import { User1UpdateComponent } from '../update/user-1-update.component';
import { User1RoutingResolveService } from './user-1-routing-resolve.service';

const user1Route: Routes = [
  {
    path: '',
    component: User1Component,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: User1DetailComponent,
    resolve: {
      user1: User1RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: User1UpdateComponent,
    resolve: {
      user1: User1RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: User1UpdateComponent,
    resolve: {
      user1: User1RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(user1Route)],
  exports: [RouterModule],
})
export class User1RoutingModule {}
