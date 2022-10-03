import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPagar } from '../pagar.model';
import { PagarService } from '../service/pagar.service';

@Component({
  templateUrl: './pagar-delete-dialog.component.html',
})
export class PagarDeleteDialogComponent {
  pagar?: IPagar;

  constructor(protected pagarService: PagarService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pagarService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
