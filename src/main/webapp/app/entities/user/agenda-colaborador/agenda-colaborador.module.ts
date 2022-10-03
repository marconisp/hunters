import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AgendaColaboradorComponent } from './list/agenda-colaborador.component';
import { AgendaColaboradorDetailComponent } from './detail/agenda-colaborador-detail.component';
import { AgendaColaboradorUpdateComponent } from './update/agenda-colaborador-update.component';
import { AgendaColaboradorDeleteDialogComponent } from './delete/agenda-colaborador-delete-dialog.component';
import { AgendaColaboradorRoutingModule } from './route/agenda-colaborador-routing.module';

@NgModule({
  imports: [SharedModule, AgendaColaboradorRoutingModule],
  declarations: [
    AgendaColaboradorComponent,
    AgendaColaboradorDetailComponent,
    AgendaColaboradorUpdateComponent,
    AgendaColaboradorDeleteDialogComponent,
  ],
  entryComponents: [AgendaColaboradorDeleteDialogComponent],
})
export class AgendaColaboradorModule {}
