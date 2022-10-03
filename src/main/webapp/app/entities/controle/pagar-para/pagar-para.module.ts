import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PagarParaComponent } from './list/pagar-para.component';
import { PagarParaDetailComponent } from './detail/pagar-para-detail.component';
import { PagarParaUpdateComponent } from './update/pagar-para-update.component';
import { PagarParaDeleteDialogComponent } from './delete/pagar-para-delete-dialog.component';
import { PagarParaRoutingModule } from './route/pagar-para-routing.module';

@NgModule({
  imports: [SharedModule, PagarParaRoutingModule],
  declarations: [PagarParaComponent, PagarParaDetailComponent, PagarParaUpdateComponent, PagarParaDeleteDialogComponent],
  entryComponents: [PagarParaDeleteDialogComponent],
})
export class PagarParaModule {}
