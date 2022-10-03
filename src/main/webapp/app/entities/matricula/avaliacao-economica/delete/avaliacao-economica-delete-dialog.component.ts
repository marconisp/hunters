import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAvaliacaoEconomica } from '../avaliacao-economica.model';
import { AvaliacaoEconomicaService } from '../service/avaliacao-economica.service';

@Component({
  templateUrl: './avaliacao-economica-delete-dialog.component.html',
})
export class AvaliacaoEconomicaDeleteDialogComponent {
  avaliacaoEconomica?: IAvaliacaoEconomica;

  constructor(protected avaliacaoEconomicaService: AvaliacaoEconomicaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.avaliacaoEconomicaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
