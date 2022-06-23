import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FotoAvatarComponent } from './list/foto-avatar.component';
import { FotoAvatarDetailComponent } from './detail/foto-avatar-detail.component';
import { FotoAvatarUpdateComponent } from './update/foto-avatar-update.component';
import { FotoAvatarDeleteDialogComponent } from './delete/foto-avatar-delete-dialog.component';
import { FotoAvatarRoutingModule } from './route/foto-avatar-routing.module';

@NgModule({
  imports: [SharedModule, FotoAvatarRoutingModule],
  declarations: [FotoAvatarComponent, FotoAvatarDetailComponent, FotoAvatarUpdateComponent, FotoAvatarDeleteDialogComponent],
  entryComponents: [FotoAvatarDeleteDialogComponent],
})
export class FotoAvatarModule {}
