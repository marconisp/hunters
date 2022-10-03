import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDiaSemana } from '../dia-semana.model';
import { DiaSemanaService } from '../service/dia-semana.service';

@Component({
  templateUrl: './dia-semana-delete-dialog.component.html',
})
export class DiaSemanaDeleteDialogComponent {
  diaSemana?: IDiaSemana;

  constructor(protected diaSemanaService: DiaSemanaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.diaSemanaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
