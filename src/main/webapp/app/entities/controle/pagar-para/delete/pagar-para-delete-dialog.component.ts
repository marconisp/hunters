import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPagarPara } from '../pagar-para.model';
import { PagarParaService } from '../service/pagar-para.service';

@Component({
  templateUrl: './pagar-para-delete-dialog.component.html',
})
export class PagarParaDeleteDialogComponent {
  pagarPara?: IPagarPara;

  constructor(protected pagarParaService: PagarParaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pagarParaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
