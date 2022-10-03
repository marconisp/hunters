import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IExameMedico } from '../exame-medico.model';
import { ExameMedicoService } from '../service/exame-medico.service';

@Component({
  templateUrl: './exame-medico-delete-dialog.component.html',
})
export class ExameMedicoDeleteDialogComponent {
  exameMedico?: IExameMedico;

  constructor(protected exameMedicoService: ExameMedicoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.exameMedicoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
