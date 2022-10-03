import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFotoProduto } from '../foto-produto.model';
import { FotoProdutoService } from '../service/foto-produto.service';

@Component({
  templateUrl: './foto-produto-delete-dialog.component.html',
})
export class FotoProdutoDeleteDialogComponent {
  fotoProduto?: IFotoProduto;

  constructor(protected fotoProdutoService: FotoProdutoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fotoProdutoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
