import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TipoContratacaoComponent } from './list/tipo-contratacao.component';
import { TipoContratacaoDetailComponent } from './detail/tipo-contratacao-detail.component';
import { TipoContratacaoUpdateComponent } from './update/tipo-contratacao-update.component';
import { TipoContratacaoDeleteDialogComponent } from './delete/tipo-contratacao-delete-dialog.component';
import { TipoContratacaoRoutingModule } from './route/tipo-contratacao-routing.module';

@NgModule({
  imports: [SharedModule, TipoContratacaoRoutingModule],
  declarations: [
    TipoContratacaoComponent,
    TipoContratacaoDetailComponent,
    TipoContratacaoUpdateComponent,
    TipoContratacaoDeleteDialogComponent,
  ],
  entryComponents: [TipoContratacaoDeleteDialogComponent],
})
export class TipoContratacaoModule {}
