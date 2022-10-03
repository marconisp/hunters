import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITipoReceber } from '../tipo-receber.model';
import { TipoReceberService } from '../service/tipo-receber.service';

@Component({
  templateUrl: './tipo-receber-delete-dialog.component.html',
})
export class TipoReceberDeleteDialogComponent {
  tipoReceber?: ITipoReceber;

  constructor(protected tipoReceberService: TipoReceberService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoReceberService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
