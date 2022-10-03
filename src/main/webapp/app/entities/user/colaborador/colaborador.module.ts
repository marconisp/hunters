import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ColaboradorComponent } from './list/colaborador.component';
import { ColaboradorDetailComponent } from './detail/colaborador-detail.component';
import { ColaboradorUpdateComponent } from './update/colaborador-update.component';
import { ColaboradorDeleteDialogComponent } from './delete/colaborador-delete-dialog.component';
import { ColaboradorRoutingModule } from './route/colaborador-routing.module';

@NgModule({
  imports: [SharedModule, ColaboradorRoutingModule],
  declarations: [ColaboradorComponent, ColaboradorDetailComponent, ColaboradorUpdateComponent, ColaboradorDeleteDialogComponent],
  entryComponents: [ColaboradorDeleteDialogComponent],
})
export class ColaboradorModule {}
