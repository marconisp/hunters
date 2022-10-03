import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TipoPagarComponent } from './list/tipo-pagar.component';
import { TipoPagarDetailComponent } from './detail/tipo-pagar-detail.component';
import { TipoPagarUpdateComponent } from './update/tipo-pagar-update.component';
import { TipoPagarDeleteDialogComponent } from './delete/tipo-pagar-delete-dialog.component';
import { TipoPagarRoutingModule } from './route/tipo-pagar-routing.module';

@NgModule({
  imports: [SharedModule, TipoPagarRoutingModule],
  declarations: [TipoPagarComponent, TipoPagarDetailComponent, TipoPagarUpdateComponent, TipoPagarDeleteDialogComponent],
  entryComponents: [TipoPagarDeleteDialogComponent],
})
export class TipoPagarModule {}
