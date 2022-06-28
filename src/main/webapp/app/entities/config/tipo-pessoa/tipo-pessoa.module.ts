import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TipoPessoaComponent } from './list/tipo-pessoa.component';
import { TipoPessoaDetailComponent } from './detail/tipo-pessoa-detail.component';
import { TipoPessoaUpdateComponent } from './update/tipo-pessoa-update.component';
import { TipoPessoaDeleteDialogComponent } from './delete/tipo-pessoa-delete-dialog.component';
import { TipoPessoaRoutingModule } from './route/tipo-pessoa-routing.module';

@NgModule({
  imports: [SharedModule, TipoPessoaRoutingModule],
  declarations: [TipoPessoaComponent, TipoPessoaDetailComponent, TipoPessoaUpdateComponent, TipoPessoaDeleteDialogComponent],
  entryComponents: [TipoPessoaDeleteDialogComponent],
})
export class TipoPessoaModule {}
