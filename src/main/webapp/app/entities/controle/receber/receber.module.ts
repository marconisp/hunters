import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ReceberComponent } from './list/receber.component';
import { ReceberDetailComponent } from './detail/receber-detail.component';
import { ReceberUpdateComponent } from './update/receber-update.component';
import { ReceberDeleteDialogComponent } from './delete/receber-delete-dialog.component';
import { ReceberReportComponent } from './report/receber-report.component';
import { ReceberRoutingModule } from './route/receber-routing.module';
import { CurrencyMaskModule } from 'ng2-currency-mask';

@NgModule({
  imports: [SharedModule, ReceberRoutingModule, CurrencyMaskModule],
  declarations: [ReceberComponent, ReceberDetailComponent, ReceberUpdateComponent, ReceberDeleteDialogComponent, ReceberReportComponent],
  entryComponents: [ReceberDeleteDialogComponent],
})
export class ReceberModule {}
