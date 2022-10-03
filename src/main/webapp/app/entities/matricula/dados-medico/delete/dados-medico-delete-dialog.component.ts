import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDadosMedico } from '../dados-medico.model';
import { DadosMedicoService } from '../service/dados-medico.service';

@Component({
  templateUrl: './dados-medico-delete-dialog.component.html',
})
export class DadosMedicoDeleteDialogComponent {
  dadosMedico?: IDadosMedico;

  constructor(protected dadosMedicoService: DadosMedicoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dadosMedicoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
