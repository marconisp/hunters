import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDadosPessoais } from '../dados-pessoais.model';
import { DadosPessoaisService } from '../service/dados-pessoais.service';

@Component({
  templateUrl: './dados-pessoais-delete-dialog.component.html',
})
export class DadosPessoaisDeleteDialogComponent {
  dadosPessoais?: IDadosPessoais;

  constructor(protected dadosPessoaisService: DadosPessoaisService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dadosPessoaisService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
