import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITipoTransacao } from '../tipo-transacao.model';
import { TipoTransacaoService } from '../service/tipo-transacao.service';

@Component({
  templateUrl: './tipo-transacao-delete-dialog.component.html',
})
export class TipoTransacaoDeleteDialogComponent {
  tipoTransacao?: ITipoTransacao;

  constructor(protected tipoTransacaoService: TipoTransacaoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoTransacaoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
