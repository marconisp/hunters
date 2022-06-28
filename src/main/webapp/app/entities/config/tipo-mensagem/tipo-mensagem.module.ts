import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TipoMensagemComponent } from './list/tipo-mensagem.component';
import { TipoMensagemDetailComponent } from './detail/tipo-mensagem-detail.component';
import { TipoMensagemUpdateComponent } from './update/tipo-mensagem-update.component';
import { TipoMensagemDeleteDialogComponent } from './delete/tipo-mensagem-delete-dialog.component';
import { TipoMensagemRoutingModule } from './route/tipo-mensagem-routing.module';

@NgModule({
  imports: [SharedModule, TipoMensagemRoutingModule],
  declarations: [TipoMensagemComponent, TipoMensagemDetailComponent, TipoMensagemUpdateComponent, TipoMensagemDeleteDialogComponent],
  entryComponents: [TipoMensagemDeleteDialogComponent],
})
export class TipoMensagemModule {}
