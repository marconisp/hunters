import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITipoPagar } from '../tipo-pagar.model';
import { TipoPagarService } from '../service/tipo-pagar.service';

@Component({
  templateUrl: './tipo-pagar-delete-dialog.component.html',
})
export class TipoPagarDeleteDialogComponent {
  tipoPagar?: ITipoPagar;

  constructor(protected tipoPagarService: TipoPagarService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoPagarService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
