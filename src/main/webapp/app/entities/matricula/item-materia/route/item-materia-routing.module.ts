import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ItemMateriaComponent } from '../list/item-materia.component';
import { ItemMateriaDetailComponent } from '../detail/item-materia-detail.component';
import { ItemMateriaUpdateComponent } from '../update/item-materia-update.component';
import { ItemMateriaRoutingResolveService } from './item-materia-routing-resolve.service';

const itemMateriaRoute: Routes = [
  {
    path: '',
    component: ItemMateriaComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ItemMateriaDetailComponent,
    resolve: {
      itemMateria: ItemMateriaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ItemMateriaUpdateComponent,
    resolve: {
      itemMateria: ItemMateriaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ItemMateriaUpdateComponent,
    resolve: {
      itemMateria: ItemMateriaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(itemMateriaRoute)],
  exports: [RouterModule],
})
export class ItemMateriaRoutingModule {}
