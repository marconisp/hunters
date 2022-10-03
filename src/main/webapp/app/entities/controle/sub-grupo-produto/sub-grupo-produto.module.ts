import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SubGrupoProdutoComponent } from './list/sub-grupo-produto.component';
import { SubGrupoProdutoDetailComponent } from './detail/sub-grupo-produto-detail.component';
import { SubGrupoProdutoUpdateComponent } from './update/sub-grupo-produto-update.component';
import { SubGrupoProdutoDeleteDialogComponent } from './delete/sub-grupo-produto-delete-dialog.component';
import { SubGrupoProdutoRoutingModule } from './route/sub-grupo-produto-routing.module';

@NgModule({
  imports: [SharedModule, SubGrupoProdutoRoutingModule],
  declarations: [
    SubGrupoProdutoComponent,
    SubGrupoProdutoDetailComponent,
    SubGrupoProdutoUpdateComponent,
    SubGrupoProdutoDeleteDialogComponent,
  ],
  entryComponents: [SubGrupoProdutoDeleteDialogComponent],
})
export class SubGrupoProdutoModule {}
