import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SaidaEstoqueComponent } from './list/saida-estoque.component';
import { SaidaEstoqueDetailComponent } from './detail/saida-estoque-detail.component';
import { SaidaEstoqueUpdateComponent } from './update/saida-estoque-update.component';
import { SaidaEstoqueDeleteDialogComponent } from './delete/saida-estoque-delete-dialog.component';
import { SaidaEstoqueRoutingModule } from './route/saida-estoque-routing.module';

@NgModule({
  imports: [SharedModule, SaidaEstoqueRoutingModule],
  declarations: [SaidaEstoqueComponent, SaidaEstoqueDetailComponent, SaidaEstoqueUpdateComponent, SaidaEstoqueDeleteDialogComponent],
  entryComponents: [SaidaEstoqueDeleteDialogComponent],
})
export class SaidaEstoqueModule {}
