import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAlergia } from '../alergia.model';
import { AlergiaService } from '../service/alergia.service';

@Component({
  templateUrl: './alergia-delete-dialog.component.html',
})
export class AlergiaDeleteDialogComponent {
  alergia?: IAlergia;

  constructor(protected alergiaService: AlergiaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.alergiaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
