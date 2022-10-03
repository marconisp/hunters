import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FotoSaidaEstoqueComponent } from './list/foto-saida-estoque.component';
import { FotoSaidaEstoqueDetailComponent } from './detail/foto-saida-estoque-detail.component';
import { FotoSaidaEstoqueUpdateComponent } from './update/foto-saida-estoque-update.component';
import { FotoSaidaEstoqueDeleteDialogComponent } from './delete/foto-saida-estoque-delete-dialog.component';
import { FotoSaidaEstoqueRoutingModule } from './route/foto-saida-estoque-routing.module';

@NgModule({
  imports: [SharedModule, FotoSaidaEstoqueRoutingModule],
  declarations: [
    FotoSaidaEstoqueComponent,
    FotoSaidaEstoqueDetailComponent,
    FotoSaidaEstoqueUpdateComponent,
    FotoSaidaEstoqueDeleteDialogComponent,
  ],
  entryComponents: [FotoSaidaEstoqueDeleteDialogComponent],
})
export class FotoSaidaEstoqueModule {}
