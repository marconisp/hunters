import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFotoReceber } from '../foto-receber.model';
import { FotoReceberService } from '../service/foto-receber.service';

@Component({
  templateUrl: './foto-receber-delete-dialog.component.html',
})
export class FotoReceberDeleteDialogComponent {
  fotoReceber?: IFotoReceber;

  constructor(protected fotoReceberService: FotoReceberService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fotoReceberService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
