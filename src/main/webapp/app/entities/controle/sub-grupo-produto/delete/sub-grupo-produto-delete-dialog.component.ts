import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISubGrupoProduto } from '../sub-grupo-produto.model';
import { SubGrupoProdutoService } from '../service/sub-grupo-produto.service';

@Component({
  templateUrl: './sub-grupo-produto-delete-dialog.component.html',
})
export class SubGrupoProdutoDeleteDialogComponent {
  subGrupoProduto?: ISubGrupoProduto;

  constructor(protected subGrupoProdutoService: SubGrupoProdutoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subGrupoProdutoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
