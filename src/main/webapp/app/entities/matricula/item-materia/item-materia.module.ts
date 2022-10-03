import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ItemMateriaComponent } from './list/item-materia.component';
import { ItemMateriaDetailComponent } from './detail/item-materia-detail.component';
import { ItemMateriaUpdateComponent } from './update/item-materia-update.component';
import { ItemMateriaDeleteDialogComponent } from './delete/item-materia-delete-dialog.component';
import { ItemMateriaRoutingModule } from './route/item-materia-routing.module';

@NgModule({
  imports: [SharedModule, ItemMateriaRoutingModule],
  declarations: [ItemMateriaComponent, ItemMateriaDetailComponent, ItemMateriaUpdateComponent, ItemMateriaDeleteDialogComponent],
  entryComponents: [ItemMateriaDeleteDialogComponent],
})
export class ItemMateriaModule {}
