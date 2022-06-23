import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RacaComponent } from './list/raca.component';
import { RacaDetailComponent } from './detail/raca-detail.component';
import { RacaUpdateComponent } from './update/raca-update.component';
import { RacaDeleteDialogComponent } from './delete/raca-delete-dialog.component';
import { RacaRoutingModule } from './route/raca-routing.module';

@NgModule({
  imports: [SharedModule, RacaRoutingModule],
  declarations: [RacaComponent, RacaDetailComponent, RacaUpdateComponent, RacaDeleteDialogComponent],
  entryComponents: [RacaDeleteDialogComponent],
})
export class RacaModule {}
