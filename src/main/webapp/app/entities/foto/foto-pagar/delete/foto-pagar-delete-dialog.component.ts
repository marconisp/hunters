import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFotoPagar } from '../foto-pagar.model';
import { FotoPagarService } from '../service/foto-pagar.service';

@Component({
  templateUrl: './foto-pagar-delete-dialog.component.html',
})
export class FotoPagarDeleteDialogComponent {
  fotoPagar?: IFotoPagar;

  constructor(protected fotoPagarService: FotoPagarService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fotoPagarService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
