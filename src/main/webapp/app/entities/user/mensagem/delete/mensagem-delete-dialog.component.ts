import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMensagem } from '../mensagem.model';
import { MensagemService } from '../service/mensagem.service';

@Component({
  templateUrl: './mensagem-delete-dialog.component.html',
})
export class MensagemDeleteDialogComponent {
  mensagem?: IMensagem;

  constructor(protected mensagemService: MensagemService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mensagemService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
