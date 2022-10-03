import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IGrupoProduto } from '../grupo-produto.model';
import { GrupoProdutoService } from '../service/grupo-produto.service';

@Component({
  templateUrl: './grupo-produto-delete-dialog.component.html',
})
export class GrupoProdutoDeleteDialogComponent {
  grupoProduto?: IGrupoProduto;

  constructor(protected grupoProdutoService: GrupoProdutoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.grupoProdutoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
