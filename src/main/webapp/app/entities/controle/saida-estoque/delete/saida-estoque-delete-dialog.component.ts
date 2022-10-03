import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISaidaEstoque } from '../saida-estoque.model';
import { SaidaEstoqueService } from '../service/saida-estoque.service';

@Component({
  templateUrl: './saida-estoque-delete-dialog.component.html',
})
export class SaidaEstoqueDeleteDialogComponent {
  saidaEstoque?: ISaidaEstoque;

  constructor(protected saidaEstoqueService: SaidaEstoqueService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.saidaEstoqueService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
