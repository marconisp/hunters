import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FotoPagarComponent } from './list/foto-pagar.component';
import { FotoPagarDetailComponent } from './detail/foto-pagar-detail.component';
import { FotoPagarUpdateComponent } from './update/foto-pagar-update.component';
import { FotoPagarDeleteDialogComponent } from './delete/foto-pagar-delete-dialog.component';
import { FotoPagarRoutingModule } from './route/foto-pagar-routing.module';

@NgModule({
  imports: [SharedModule, FotoPagarRoutingModule],
  declarations: [FotoPagarComponent, FotoPagarDetailComponent, FotoPagarUpdateComponent, FotoPagarDeleteDialogComponent],
  entryComponents: [FotoPagarDeleteDialogComponent],
})
export class FotoPagarModule {}
