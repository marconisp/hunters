import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFotoSaidaEstoque } from '../foto-saida-estoque.model';
import { FotoSaidaEstoqueService } from '../service/foto-saida-estoque.service';

@Component({
  templateUrl: './foto-saida-estoque-delete-dialog.component.html',
})
export class FotoSaidaEstoqueDeleteDialogComponent {
  fotoSaidaEstoque?: IFotoSaidaEstoque;

  constructor(protected fotoSaidaEstoqueService: FotoSaidaEstoqueService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fotoSaidaEstoqueService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
