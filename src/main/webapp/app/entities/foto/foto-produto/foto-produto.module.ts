import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FotoProdutoComponent } from './list/foto-produto.component';
import { FotoProdutoDetailComponent } from './detail/foto-produto-detail.component';
import { FotoProdutoUpdateComponent } from './update/foto-produto-update.component';
import { FotoProdutoDeleteDialogComponent } from './delete/foto-produto-delete-dialog.component';
import { FotoProdutoRoutingModule } from './route/foto-produto-routing.module';

@NgModule({
  imports: [SharedModule, FotoProdutoRoutingModule],
  declarations: [FotoProdutoComponent, FotoProdutoDetailComponent, FotoProdutoUpdateComponent, FotoProdutoDeleteDialogComponent],
  entryComponents: [FotoProdutoDeleteDialogComponent],
})
export class FotoProdutoModule {}
