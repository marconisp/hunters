import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ExameMedicoComponent } from './list/exame-medico.component';
import { ExameMedicoDetailComponent } from './detail/exame-medico-detail.component';
import { ExameMedicoUpdateComponent } from './update/exame-medico-update.component';
import { ExameMedicoDeleteDialogComponent } from './delete/exame-medico-delete-dialog.component';
import { ExameMedicoRoutingModule } from './route/exame-medico-routing.module';

@NgModule({
  imports: [SharedModule, ExameMedicoRoutingModule],
  declarations: [ExameMedicoComponent, ExameMedicoDetailComponent, ExameMedicoUpdateComponent, ExameMedicoDeleteDialogComponent],
  entryComponents: [ExameMedicoDeleteDialogComponent],
})
export class ExameMedicoModule {}
