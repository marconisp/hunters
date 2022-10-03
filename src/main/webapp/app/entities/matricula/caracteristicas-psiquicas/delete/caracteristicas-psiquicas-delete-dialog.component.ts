import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICaracteristicasPsiquicas } from '../caracteristicas-psiquicas.model';
import { CaracteristicasPsiquicasService } from '../service/caracteristicas-psiquicas.service';

@Component({
  templateUrl: './caracteristicas-psiquicas-delete-dialog.component.html',
})
export class CaracteristicasPsiquicasDeleteDialogComponent {
  caracteristicasPsiquicas?: ICaracteristicasPsiquicas;

  constructor(protected caracteristicasPsiquicasService: CaracteristicasPsiquicasService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.caracteristicasPsiquicasService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
