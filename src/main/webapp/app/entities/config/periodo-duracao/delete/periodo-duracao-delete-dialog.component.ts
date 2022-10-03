import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPeriodoDuracao } from '../periodo-duracao.model';
import { PeriodoDuracaoService } from '../service/periodo-duracao.service';

@Component({
  templateUrl: './periodo-duracao-delete-dialog.component.html',
})
export class PeriodoDuracaoDeleteDialogComponent {
  periodoDuracao?: IPeriodoDuracao;

  constructor(protected periodoDuracaoService: PeriodoDuracaoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.periodoDuracaoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
