import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFotoEntradaEstoque } from '../foto-entrada-estoque.model';
import { FotoEntradaEstoqueService } from '../service/foto-entrada-estoque.service';

@Component({
  templateUrl: './foto-entrada-estoque-delete-dialog.component.html',
})
export class FotoEntradaEstoqueDeleteDialogComponent {
  fotoEntradaEstoque?: IFotoEntradaEstoque;

  constructor(protected fotoEntradaEstoqueService: FotoEntradaEstoqueService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fotoEntradaEstoqueService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
