import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TipoTransacaoComponent } from './list/tipo-transacao.component';
import { TipoTransacaoDetailComponent } from './detail/tipo-transacao-detail.component';
import { TipoTransacaoUpdateComponent } from './update/tipo-transacao-update.component';
import { TipoTransacaoDeleteDialogComponent } from './delete/tipo-transacao-delete-dialog.component';
import { TipoTransacaoRoutingModule } from './route/tipo-transacao-routing.module';

@NgModule({
  imports: [SharedModule, TipoTransacaoRoutingModule],
  declarations: [TipoTransacaoComponent, TipoTransacaoDetailComponent, TipoTransacaoUpdateComponent, TipoTransacaoDeleteDialogComponent],
  entryComponents: [TipoTransacaoDeleteDialogComponent],
})
export class TipoTransacaoModule {}
