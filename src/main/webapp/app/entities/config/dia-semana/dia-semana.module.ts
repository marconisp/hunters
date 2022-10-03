import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DiaSemanaComponent } from './list/dia-semana.component';
import { DiaSemanaDetailComponent } from './detail/dia-semana-detail.component';
import { DiaSemanaUpdateComponent } from './update/dia-semana-update.component';
import { DiaSemanaDeleteDialogComponent } from './delete/dia-semana-delete-dialog.component';
import { DiaSemanaRoutingModule } from './route/dia-semana-routing.module';

@NgModule({
  imports: [SharedModule, DiaSemanaRoutingModule],
  declarations: [DiaSemanaComponent, DiaSemanaDetailComponent, DiaSemanaUpdateComponent, DiaSemanaDeleteDialogComponent],
  entryComponents: [DiaSemanaDeleteDialogComponent],
})
export class DiaSemanaModule {}
