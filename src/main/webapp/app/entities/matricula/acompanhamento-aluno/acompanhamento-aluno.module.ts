import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AcompanhamentoAlunoComponent } from './list/acompanhamento-aluno.component';
import { AcompanhamentoAlunoDetailComponent } from './detail/acompanhamento-aluno-detail.component';
import { AcompanhamentoAlunoUpdateComponent } from './update/acompanhamento-aluno-update.component';
import { AcompanhamentoAlunoDeleteDialogComponent } from './delete/acompanhamento-aluno-delete-dialog.component';
import { AcompanhamentoAlunoRoutingModule } from './route/acompanhamento-aluno-routing.module';

@NgModule({
  imports: [SharedModule, AcompanhamentoAlunoRoutingModule],
  declarations: [
    AcompanhamentoAlunoComponent,
    AcompanhamentoAlunoDetailComponent,
    AcompanhamentoAlunoUpdateComponent,
    AcompanhamentoAlunoDeleteDialogComponent,
  ],
  entryComponents: [AcompanhamentoAlunoDeleteDialogComponent],
})
export class AcompanhamentoAlunoModule {}
