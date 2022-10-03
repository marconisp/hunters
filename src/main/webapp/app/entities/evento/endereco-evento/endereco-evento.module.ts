import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EnderecoEventoComponent } from './list/endereco-evento.component';
import { EnderecoEventoDetailComponent } from './detail/endereco-evento-detail.component';
import { EnderecoEventoUpdateComponent } from './update/endereco-evento-update.component';
import { EnderecoEventoDeleteDialogComponent } from './delete/endereco-evento-delete-dialog.component';
import { EnderecoEventoRoutingModule } from './route/endereco-evento-routing.module';

@NgModule({
  imports: [SharedModule, EnderecoEventoRoutingModule],
  declarations: [
    EnderecoEventoComponent,
    EnderecoEventoDetailComponent,
    EnderecoEventoUpdateComponent,
    EnderecoEventoDeleteDialogComponent,
  ],
  entryComponents: [EnderecoEventoDeleteDialogComponent],
})
export class EnderecoEventoModule {}
