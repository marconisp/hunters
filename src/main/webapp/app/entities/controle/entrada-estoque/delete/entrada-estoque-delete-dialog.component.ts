import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEntradaEstoque } from '../entrada-estoque.model';
import { EntradaEstoqueService } from '../service/entrada-estoque.service';

@Component({
  templateUrl: './entrada-estoque-delete-dialog.component.html',
})
export class EntradaEstoqueDeleteDialogComponent {
  entradaEstoque?: IEntradaEstoque;

  constructor(protected entradaEstoqueService: EntradaEstoqueService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.entradaEstoqueService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
