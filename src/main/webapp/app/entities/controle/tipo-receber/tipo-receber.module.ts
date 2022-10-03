import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TipoReceberComponent } from './list/tipo-receber.component';
import { TipoReceberDetailComponent } from './detail/tipo-receber-detail.component';
import { TipoReceberUpdateComponent } from './update/tipo-receber-update.component';
import { TipoReceberDeleteDialogComponent } from './delete/tipo-receber-delete-dialog.component';
import { TipoReceberRoutingModule } from './route/tipo-receber-routing.module';

@NgModule({
  imports: [SharedModule, TipoReceberRoutingModule],
  declarations: [TipoReceberComponent, TipoReceberDetailComponent, TipoReceberUpdateComponent, TipoReceberDeleteDialogComponent],
  entryComponents: [TipoReceberDeleteDialogComponent],
})
export class TipoReceberModule {}
