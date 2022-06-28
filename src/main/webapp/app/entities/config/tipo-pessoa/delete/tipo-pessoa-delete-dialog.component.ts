import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITipoPessoa } from '../tipo-pessoa.model';
import { TipoPessoaService } from '../service/tipo-pessoa.service';

@Component({
  templateUrl: './tipo-pessoa-delete-dialog.component.html',
})
export class TipoPessoaDeleteDialogComponent {
  tipoPessoa?: ITipoPessoa;

  constructor(protected tipoPessoaService: TipoPessoaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoPessoaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
