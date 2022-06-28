import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFotoIcon } from '../foto-icon.model';
import { FotoIconService } from '../service/foto-icon.service';

@Component({
  templateUrl: './foto-icon-delete-dialog.component.html',
})
export class FotoIconDeleteDialogComponent {
  fotoIcon?: IFotoIcon;

  constructor(protected fotoIconService: FotoIconService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fotoIconService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
