import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AvisoComponent } from './list/aviso.component';
import { AvisoDetailComponent } from './detail/aviso-detail.component';
import { AvisoUpdateComponent } from './update/aviso-update.component';
import { AvisoDeleteDialogComponent } from './delete/aviso-delete-dialog.component';
import { AvisoRoutingModule } from './route/aviso-routing.module';

@NgModule({
  imports: [SharedModule, AvisoRoutingModule],
  declarations: [AvisoComponent, AvisoDetailComponent, AvisoUpdateComponent, AvisoDeleteDialogComponent],
  entryComponents: [AvisoDeleteDialogComponent],
})
export class AvisoModule {}
