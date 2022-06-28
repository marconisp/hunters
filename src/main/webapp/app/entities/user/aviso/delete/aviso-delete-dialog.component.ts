import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAviso } from '../aviso.model';
import { AvisoService } from '../service/aviso.service';

@Component({
  templateUrl: './aviso-delete-dialog.component.html',
})
export class AvisoDeleteDialogComponent {
  aviso?: IAviso;

  constructor(protected avisoService: AvisoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.avisoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
