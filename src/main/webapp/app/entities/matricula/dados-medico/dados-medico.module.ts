import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DadosMedicoComponent } from './list/dados-medico.component';
import { DadosMedicoDetailComponent } from './detail/dados-medico-detail.component';
import { DadosMedicoUpdateComponent } from './update/dados-medico-update.component';
import { DadosMedicoDeleteDialogComponent } from './delete/dados-medico-delete-dialog.component';
import { DadosMedicoRoutingModule } from './route/dados-medico-routing.module';

@NgModule({
  imports: [SharedModule, DadosMedicoRoutingModule],
  declarations: [DadosMedicoComponent, DadosMedicoDetailComponent, DadosMedicoUpdateComponent, DadosMedicoDeleteDialogComponent],
  entryComponents: [DadosMedicoDeleteDialogComponent],
})
export class DadosMedicoModule {}
