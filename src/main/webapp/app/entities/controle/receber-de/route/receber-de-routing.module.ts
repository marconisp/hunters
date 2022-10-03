import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ReceberDeComponent } from '../list/receber-de.component';
import { ReceberDeDetailComponent } from '../detail/receber-de-detail.component';
import { ReceberDeUpdateComponent } from '../update/receber-de-update.component';
import { ReceberDeRoutingResolveService } from './receber-de-routing-resolve.service';

const receberDeRoute: Routes = [
  {
    path: '',
    component: ReceberDeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ReceberDeDetailComponent,
    resolve: {
      receberDe: ReceberDeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ReceberDeUpdateComponent,
    resolve: {
      receberDe: ReceberDeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ReceberDeUpdateComponent,
    resolve: {
      receberDe: ReceberDeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(receberDeRoute)],
  exports: [RouterModule],
})
export class ReceberDeRoutingModule {}
