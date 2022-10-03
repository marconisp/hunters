import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITipoContratacao } from '../tipo-contratacao.model';
import { TipoContratacaoService } from '../service/tipo-contratacao.service';

@Component({
  templateUrl: './tipo-contratacao-delete-dialog.component.html',
})
export class TipoContratacaoDeleteDialogComponent {
  tipoContratacao?: ITipoContratacao;

  constructor(protected tipoContratacaoService: TipoContratacaoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoContratacaoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
