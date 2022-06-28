import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { User1Component } from './list/user-1.component';
import { User1DetailComponent } from './detail/user-1-detail.component';
import { User1UpdateComponent } from './update/user-1-update.component';
import { User1DeleteDialogComponent } from './delete/user-1-delete-dialog.component';
import { User1RoutingModule } from './route/user-1-routing.module';

@NgModule({
  imports: [SharedModule, User1RoutingModule],
  declarations: [User1Component, User1DetailComponent, User1UpdateComponent, User1DeleteDialogComponent],
  entryComponents: [User1DeleteDialogComponent],
})
export class User1Module {}
