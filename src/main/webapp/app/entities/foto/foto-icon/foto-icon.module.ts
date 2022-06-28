import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FotoIconComponent } from './list/foto-icon.component';
import { FotoIconDetailComponent } from './detail/foto-icon-detail.component';
import { FotoIconUpdateComponent } from './update/foto-icon-update.component';
import { FotoIconDeleteDialogComponent } from './delete/foto-icon-delete-dialog.component';
import { FotoIconRoutingModule } from './route/foto-icon-routing.module';

@NgModule({
  imports: [SharedModule, FotoIconRoutingModule],
  declarations: [FotoIconComponent, FotoIconDetailComponent, FotoIconUpdateComponent, FotoIconDeleteDialogComponent],
  entryComponents: [FotoIconDeleteDialogComponent],
})
export class FotoIconModule {}
