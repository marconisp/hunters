import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IItemMateria } from '../item-materia.model';
import { ItemMateriaService } from '../service/item-materia.service';

@Component({
  templateUrl: './item-materia-delete-dialog.component.html',
})
export class ItemMateriaDeleteDialogComponent {
  itemMateria?: IItemMateria;

  constructor(protected itemMateriaService: ItemMateriaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.itemMateriaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
