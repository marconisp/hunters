import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITipoMensagem } from '../tipo-mensagem.model';
import { TipoMensagemService } from '../service/tipo-mensagem.service';

@Component({
  templateUrl: './tipo-mensagem-delete-dialog.component.html',
})
export class TipoMensagemDeleteDialogComponent {
  tipoMensagem?: ITipoMensagem;

  constructor(protected tipoMensagemService: TipoMensagemService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoMensagemService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
