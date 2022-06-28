import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FotoDocumentoComponent } from './list/foto-documento.component';
import { FotoDocumentoDetailComponent } from './detail/foto-documento-detail.component';
import { FotoDocumentoUpdateComponent } from './update/foto-documento-update.component';
import { FotoDocumentoDeleteDialogComponent } from './delete/foto-documento-delete-dialog.component';
import { FotoDocumentoRoutingModule } from './route/foto-documento-routing.module';

@NgModule({
  imports: [SharedModule, FotoDocumentoRoutingModule],
  declarations: [FotoDocumentoComponent, FotoDocumentoDetailComponent, FotoDocumentoUpdateComponent, FotoDocumentoDeleteDialogComponent],
  entryComponents: [FotoDocumentoDeleteDialogComponent],
})
export class FotoDocumentoModule {}
