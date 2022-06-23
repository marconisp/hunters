import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUser1 } from '../user-1.model';
import { User1Service } from '../service/user-1.service';

@Component({
  templateUrl: './user-1-delete-dialog.component.html',
})
export class User1DeleteDialogComponent {
  user1?: IUser1;

  constructor(protected user1Service: User1Service, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.user1Service.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
