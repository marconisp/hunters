import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAcompanhamentoAluno } from '../acompanhamento-aluno.model';
import { AcompanhamentoAlunoService } from '../service/acompanhamento-aluno.service';

@Component({
  templateUrl: './acompanhamento-aluno-delete-dialog.component.html',
})
export class AcompanhamentoAlunoDeleteDialogComponent {
  acompanhamentoAluno?: IAcompanhamentoAluno;

  constructor(protected acompanhamentoAlunoService: AcompanhamentoAlunoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.acompanhamentoAlunoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
