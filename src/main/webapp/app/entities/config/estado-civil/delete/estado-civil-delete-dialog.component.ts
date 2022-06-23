import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEstadoCivil } from '../estado-civil.model';
import { EstadoCivilService } from '../service/estado-civil.service';

@Component({
  templateUrl: './estado-civil-delete-dialog.component.html',
})
export class EstadoCivilDeleteDialogComponent {
  estadoCivil?: IEstadoCivil;

  constructor(protected estadoCivilService: EstadoCivilService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.estadoCivilService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
