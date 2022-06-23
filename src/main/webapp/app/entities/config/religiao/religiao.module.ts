import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ReligiaoComponent } from './list/religiao.component';
import { ReligiaoDetailComponent } from './detail/religiao-detail.component';
import { ReligiaoUpdateComponent } from './update/religiao-update.component';
import { ReligiaoDeleteDialogComponent } from './delete/religiao-delete-dialog.component';
import { ReligiaoRoutingModule } from './route/religiao-routing.module';

@NgModule({
  imports: [SharedModule, ReligiaoRoutingModule],
  declarations: [ReligiaoComponent, ReligiaoDetailComponent, ReligiaoUpdateComponent, ReligiaoDeleteDialogComponent],
  entryComponents: [ReligiaoDeleteDialogComponent],
})
export class ReligiaoModule {}
