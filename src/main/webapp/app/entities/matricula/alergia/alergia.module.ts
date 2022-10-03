import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AlergiaComponent } from './list/alergia.component';
import { AlergiaDetailComponent } from './detail/alergia-detail.component';
import { AlergiaUpdateComponent } from './update/alergia-update.component';
import { AlergiaDeleteDialogComponent } from './delete/alergia-delete-dialog.component';
import { AlergiaRoutingModule } from './route/alergia-routing.module';

@NgModule({
  imports: [SharedModule, AlergiaRoutingModule],
  declarations: [AlergiaComponent, AlergiaDetailComponent, AlergiaUpdateComponent, AlergiaDeleteDialogComponent],
  entryComponents: [AlergiaDeleteDialogComponent],
})
export class AlergiaModule {}
