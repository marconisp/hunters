import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRaca } from '../raca.model';
import { RacaService } from '../service/raca.service';

@Component({
  templateUrl: './raca-delete-dialog.component.html',
})
export class RacaDeleteDialogComponent {
  raca?: IRaca;

  constructor(protected racaService: RacaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.racaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
