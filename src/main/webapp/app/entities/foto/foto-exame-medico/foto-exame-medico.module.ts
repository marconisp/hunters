import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FotoExameMedicoComponent } from './list/foto-exame-medico.component';
import { FotoExameMedicoDetailComponent } from './detail/foto-exame-medico-detail.component';
import { FotoExameMedicoUpdateComponent } from './update/foto-exame-medico-update.component';
import { FotoExameMedicoDeleteDialogComponent } from './delete/foto-exame-medico-delete-dialog.component';
import { FotoExameMedicoRoutingModule } from './route/foto-exame-medico-routing.module';

@NgModule({
  imports: [SharedModule, FotoExameMedicoRoutingModule],
  declarations: [
    FotoExameMedicoComponent,
    FotoExameMedicoDetailComponent,
    FotoExameMedicoUpdateComponent,
    FotoExameMedicoDeleteDialogComponent,
  ],
  entryComponents: [FotoExameMedicoDeleteDialogComponent],
})
export class FotoExameMedicoModule {}
