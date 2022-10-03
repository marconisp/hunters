import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CaracteristicasPsiquicasComponent } from './list/caracteristicas-psiquicas.component';
import { CaracteristicasPsiquicasDetailComponent } from './detail/caracteristicas-psiquicas-detail.component';
import { CaracteristicasPsiquicasUpdateComponent } from './update/caracteristicas-psiquicas-update.component';
import { CaracteristicasPsiquicasDeleteDialogComponent } from './delete/caracteristicas-psiquicas-delete-dialog.component';
import { CaracteristicasPsiquicasRoutingModule } from './route/caracteristicas-psiquicas-routing.module';

@NgModule({
  imports: [SharedModule, CaracteristicasPsiquicasRoutingModule],
  declarations: [
    CaracteristicasPsiquicasComponent,
    CaracteristicasPsiquicasDetailComponent,
    CaracteristicasPsiquicasUpdateComponent,
    CaracteristicasPsiquicasDeleteDialogComponent,
  ],
  entryComponents: [CaracteristicasPsiquicasDeleteDialogComponent],
})
export class CaracteristicasPsiquicasModule {}
