import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IReceber } from '../receber.model';
import { ReceberService } from '../service/receber.service';

@Component({
  templateUrl: './receber-delete-dialog.component.html',
})
export class ReceberDeleteDialogComponent {
  receber?: IReceber;

  constructor(protected receberService: ReceberService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.receberService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
