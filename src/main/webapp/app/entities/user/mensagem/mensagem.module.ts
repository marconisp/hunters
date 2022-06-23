import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MensagemComponent } from './list/mensagem.component';
import { MensagemDetailComponent } from './detail/mensagem-detail.component';
import { MensagemUpdateComponent } from './update/mensagem-update.component';
import { MensagemDeleteDialogComponent } from './delete/mensagem-delete-dialog.component';
import { MensagemRoutingModule } from './route/mensagem-routing.module';

@NgModule({
  imports: [SharedModule, MensagemRoutingModule],
  declarations: [MensagemComponent, MensagemDetailComponent, MensagemUpdateComponent, MensagemDeleteDialogComponent],
  entryComponents: [MensagemDeleteDialogComponent],
})
export class MensagemModule {}
