import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EntradaEstoqueComponent } from './list/entrada-estoque.component';
import { EntradaEstoqueDetailComponent } from './detail/entrada-estoque-detail.component';
import { EntradaEstoqueUpdateComponent } from './update/entrada-estoque-update.component';
import { EntradaEstoqueDeleteDialogComponent } from './delete/entrada-estoque-delete-dialog.component';
import { EntradaEstoqueRoutingModule } from './route/entrada-estoque-routing.module';

@NgModule({
  imports: [SharedModule, EntradaEstoqueRoutingModule],
  declarations: [
    EntradaEstoqueComponent,
    EntradaEstoqueDetailComponent,
    EntradaEstoqueUpdateComponent,
    EntradaEstoqueDeleteDialogComponent,
  ],
  entryComponents: [EntradaEstoqueDeleteDialogComponent],
})
export class EntradaEstoqueModule {}
