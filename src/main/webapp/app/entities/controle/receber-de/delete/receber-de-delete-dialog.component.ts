import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IReceberDe } from '../receber-de.model';
import { ReceberDeService } from '../service/receber-de.service';

@Component({
  templateUrl: './receber-de-delete-dialog.component.html',
})
export class ReceberDeDeleteDialogComponent {
  receberDe?: IReceberDe;

  constructor(protected receberDeService: ReceberDeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.receberDeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
