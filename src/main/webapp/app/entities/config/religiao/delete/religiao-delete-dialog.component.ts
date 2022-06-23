import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IReligiao } from '../religiao.model';
import { ReligiaoService } from '../service/religiao.service';

@Component({
  templateUrl: './religiao-delete-dialog.component.html',
})
export class ReligiaoDeleteDialogComponent {
  religiao?: IReligiao;

  constructor(protected religiaoService: ReligiaoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.religiaoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
