import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ReceberDeComponent } from './list/receber-de.component';
import { ReceberDeDetailComponent } from './detail/receber-de-detail.component';
import { ReceberDeUpdateComponent } from './update/receber-de-update.component';
import { ReceberDeDeleteDialogComponent } from './delete/receber-de-delete-dialog.component';
import { ReceberDeRoutingModule } from './route/receber-de-routing.module';

@NgModule({
  imports: [SharedModule, ReceberDeRoutingModule],
  declarations: [ReceberDeComponent, ReceberDeDetailComponent, ReceberDeUpdateComponent, ReceberDeDeleteDialogComponent],
  entryComponents: [ReceberDeDeleteDialogComponent],
})
export class ReceberDeModule {}
