import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PeriodoDuracaoComponent } from './list/periodo-duracao.component';
import { PeriodoDuracaoDetailComponent } from './detail/periodo-duracao-detail.component';
import { PeriodoDuracaoUpdateComponent } from './update/periodo-duracao-update.component';
import { PeriodoDuracaoDeleteDialogComponent } from './delete/periodo-duracao-delete-dialog.component';
import { PeriodoDuracaoRoutingModule } from './route/periodo-duracao-routing.module';

@NgModule({
  imports: [SharedModule, PeriodoDuracaoRoutingModule],
  declarations: [
    PeriodoDuracaoComponent,
    PeriodoDuracaoDetailComponent,
    PeriodoDuracaoUpdateComponent,
    PeriodoDuracaoDeleteDialogComponent,
  ],
  entryComponents: [PeriodoDuracaoDeleteDialogComponent],
})
export class PeriodoDuracaoModule {}
