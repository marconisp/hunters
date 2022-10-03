import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAgendaColaborador } from '../agenda-colaborador.model';
import { AgendaColaboradorService } from '../service/agenda-colaborador.service';

@Component({
  templateUrl: './agenda-colaborador-delete-dialog.component.html',
})
export class AgendaColaboradorDeleteDialogComponent {
  agendaColaborador?: IAgendaColaborador;

  constructor(protected agendaColaboradorService: AgendaColaboradorService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.agendaColaboradorService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
