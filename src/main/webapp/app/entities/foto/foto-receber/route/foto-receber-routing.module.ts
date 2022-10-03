import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FotoReceberComponent } from '../list/foto-receber.component';
import { FotoReceberDetailComponent } from '../detail/foto-receber-detail.component';
import { FotoReceberUpdateComponent } from '../update/foto-receber-update.component';
import { FotoReceberRoutingResolveService } from './foto-receber-routing-resolve.service';
import { FotoReceberRoutingResolveReceberService } from './foto-receber-routing-resolve.receberservice';

const fotoReceberRoute: Routes = [
  {
    path: '',
    component: FotoReceberComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'receber/:idReceber',
    component: FotoReceberComponent,
    data: {
      defaultSort: 'id,asc',
    },
    resolve: {
      receber: FotoReceberRoutingResolveReceberService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FotoReceberDetailComponent,
    resolve: {
      fotoReceber: FotoReceberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FotoReceberUpdateComponent,
    resolve: {
      fotoReceber: FotoReceberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new/receber/:idReceber',
    component: FotoReceberUpdateComponent,
    resolve: {
      fotoReceber: FotoReceberRoutingResolveService,
      receber: FotoReceberRoutingResolveReceberService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FotoReceberUpdateComponent,
    resolve: {
      fotoReceber: FotoReceberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fotoReceberRoute)],
  exports: [RouterModule],
})
export class FotoReceberRoutingModule {}
