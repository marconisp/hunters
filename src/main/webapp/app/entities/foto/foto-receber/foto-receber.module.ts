import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FotoReceberComponent } from './list/foto-receber.component';
import { FotoReceberDetailComponent } from './detail/foto-receber-detail.component';
import { FotoReceberUpdateComponent } from './update/foto-receber-update.component';
import { FotoReceberDeleteDialogComponent } from './delete/foto-receber-delete-dialog.component';
import { FotoReceberRoutingModule } from './route/foto-receber-routing.module';

@NgModule({
  imports: [SharedModule, FotoReceberRoutingModule],
  declarations: [FotoReceberComponent, FotoReceberDetailComponent, FotoReceberUpdateComponent, FotoReceberDeleteDialogComponent],
  entryComponents: [FotoReceberDeleteDialogComponent],
})
export class FotoReceberModule {}
