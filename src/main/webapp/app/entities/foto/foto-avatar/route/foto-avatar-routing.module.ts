import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FotoAvatarComponent } from '../list/foto-avatar.component';
import { FotoAvatarDetailComponent } from '../detail/foto-avatar-detail.component';
import { FotoAvatarUpdateComponent } from '../update/foto-avatar-update.component';
import { FotoAvatarRoutingResolveService } from './foto-avatar-routing-resolve.service';

const fotoAvatarRoute: Routes = [
  {
    path: '',
    component: FotoAvatarComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FotoAvatarDetailComponent,
    resolve: {
      fotoAvatar: FotoAvatarRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FotoAvatarUpdateComponent,
    resolve: {
      fotoAvatar: FotoAvatarRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FotoAvatarUpdateComponent,
    resolve: {
      fotoAvatar: FotoAvatarRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fotoAvatarRoute)],
  exports: [RouterModule],
})
export class FotoAvatarRoutingModule {}
