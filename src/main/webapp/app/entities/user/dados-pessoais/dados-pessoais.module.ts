import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DadosPessoaisComponent } from './list/dados-pessoais.component';
import { DadosPessoaisDetailComponent } from './detail/dados-pessoais-detail.component';
import { DadosPessoaisUpdateComponent } from './update/dados-pessoais-update.component';
import { DadosPessoaisDeleteDialogComponent } from './delete/dados-pessoais-delete-dialog.component';
import { DadosPessoaisRoutingModule } from './route/dados-pessoais-routing.module';

@NgModule({
  imports: [SharedModule, DadosPessoaisRoutingModule],
  declarations: [DadosPessoaisComponent, DadosPessoaisDetailComponent, DadosPessoaisUpdateComponent, DadosPessoaisDeleteDialogComponent],
  entryComponents: [DadosPessoaisDeleteDialogComponent],
})
export class DadosPessoaisModule {}
