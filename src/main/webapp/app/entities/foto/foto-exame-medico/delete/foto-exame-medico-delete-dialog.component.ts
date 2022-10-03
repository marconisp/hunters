import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFotoExameMedico } from '../foto-exame-medico.model';
import { FotoExameMedicoService } from '../service/foto-exame-medico.service';

@Component({
  templateUrl: './foto-exame-medico-delete-dialog.component.html',
})
export class FotoExameMedicoDeleteDialogComponent {
  fotoExameMedico?: IFotoExameMedico;

  constructor(protected fotoExameMedicoService: FotoExameMedicoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fotoExameMedicoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
