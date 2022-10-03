import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FotoEntradaEstoqueComponent } from './list/foto-entrada-estoque.component';
import { FotoEntradaEstoqueDetailComponent } from './detail/foto-entrada-estoque-detail.component';
import { FotoEntradaEstoqueUpdateComponent } from './update/foto-entrada-estoque-update.component';
import { FotoEntradaEstoqueDeleteDialogComponent } from './delete/foto-entrada-estoque-delete-dialog.component';
import { FotoEntradaEstoqueRoutingModule } from './route/foto-entrada-estoque-routing.module';

@NgModule({
  imports: [SharedModule, FotoEntradaEstoqueRoutingModule],
  declarations: [
    FotoEntradaEstoqueComponent,
    FotoEntradaEstoqueDetailComponent,
    FotoEntradaEstoqueUpdateComponent,
    FotoEntradaEstoqueDeleteDialogComponent,
  ],
  entryComponents: [FotoEntradaEstoqueDeleteDialogComponent],
})
export class FotoEntradaEstoqueModule {}
