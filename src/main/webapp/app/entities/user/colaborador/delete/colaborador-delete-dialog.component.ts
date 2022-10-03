import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IColaborador } from '../colaborador.model';
import { ColaboradorService } from '../service/colaborador.service';

@Component({
  templateUrl: './colaborador-delete-dialog.component.html',
})
export class ColaboradorDeleteDialogComponent {
  colaborador?: IColaborador;

  constructor(protected colaboradorService: ColaboradorService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.colaboradorService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
