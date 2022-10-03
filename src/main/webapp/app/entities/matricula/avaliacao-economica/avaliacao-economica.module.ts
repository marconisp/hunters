import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AvaliacaoEconomicaComponent } from './list/avaliacao-economica.component';
import { AvaliacaoEconomicaDetailComponent } from './detail/avaliacao-economica-detail.component';
import { AvaliacaoEconomicaUpdateComponent } from './update/avaliacao-economica-update.component';
import { AvaliacaoEconomicaDeleteDialogComponent } from './delete/avaliacao-economica-delete-dialog.component';
import { AvaliacaoEconomicaRoutingModule } from './route/avaliacao-economica-routing.module';

@NgModule({
  imports: [SharedModule, AvaliacaoEconomicaRoutingModule],
  declarations: [
    AvaliacaoEconomicaComponent,
    AvaliacaoEconomicaDetailComponent,
    AvaliacaoEconomicaUpdateComponent,
    AvaliacaoEconomicaDeleteDialogComponent,
  ],
  entryComponents: [AvaliacaoEconomicaDeleteDialogComponent],
})
export class AvaliacaoEconomicaModule {}
