import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEnderecoEvento } from '../endereco-evento.model';
import { EnderecoEventoService } from '../service/endereco-evento.service';

@Component({
  templateUrl: './endereco-evento-delete-dialog.component.html',
})
export class EnderecoEventoDeleteDialogComponent {
  enderecoEvento?: IEnderecoEvento;

  constructor(protected enderecoEventoService: EnderecoEventoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.enderecoEventoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
