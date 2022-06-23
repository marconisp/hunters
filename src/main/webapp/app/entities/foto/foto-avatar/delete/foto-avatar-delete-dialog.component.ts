import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFotoAvatar } from '../foto-avatar.model';
import { FotoAvatarService } from '../service/foto-avatar.service';

@Component({
  templateUrl: './foto-avatar-delete-dialog.component.html',
})
export class FotoAvatarDeleteDialogComponent {
  fotoAvatar?: IFotoAvatar;

  constructor(protected fotoAvatarService: FotoAvatarService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fotoAvatarService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
