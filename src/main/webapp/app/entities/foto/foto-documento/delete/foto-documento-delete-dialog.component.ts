import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFotoDocumento } from '../foto-documento.model';
import { FotoDocumentoService } from '../service/foto-documento.service';

@Component({
  templateUrl: './foto-documento-delete-dialog.component.html',
})
export class FotoDocumentoDeleteDialogComponent {
  fotoDocumento?: IFotoDocumento;

  constructor(protected fotoDocumentoService: FotoDocumentoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fotoDocumentoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
