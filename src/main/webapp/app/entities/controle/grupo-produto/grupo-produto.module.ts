import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { GrupoProdutoComponent } from './list/grupo-produto.component';
import { GrupoProdutoDetailComponent } from './detail/grupo-produto-detail.component';
import { GrupoProdutoUpdateComponent } from './update/grupo-produto-update.component';
import { GrupoProdutoDeleteDialogComponent } from './delete/grupo-produto-delete-dialog.component';
import { GrupoProdutoRoutingModule } from './route/grupo-produto-routing.module';

@NgModule({
  imports: [SharedModule, GrupoProdutoRoutingModule],
  declarations: [GrupoProdutoComponent, GrupoProdutoDetailComponent, GrupoProdutoUpdateComponent, GrupoProdutoDeleteDialogComponent],
  entryComponents: [GrupoProdutoDeleteDialogComponent],
})
export class GrupoProdutoModule {}
