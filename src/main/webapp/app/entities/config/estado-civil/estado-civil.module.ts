import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EstadoCivilComponent } from './list/estado-civil.component';
import { EstadoCivilDetailComponent } from './detail/estado-civil-detail.component';
import { EstadoCivilUpdateComponent } from './update/estado-civil-update.component';
import { EstadoCivilDeleteDialogComponent } from './delete/estado-civil-delete-dialog.component';
import { EstadoCivilRoutingModule } from './route/estado-civil-routing.module';

@NgModule({
  imports: [SharedModule, EstadoCivilRoutingModule],
  declarations: [EstadoCivilComponent, EstadoCivilDetailComponent, EstadoCivilUpdateComponent, EstadoCivilDeleteDialogComponent],
  entryComponents: [EstadoCivilDeleteDialogComponent],
})
export class EstadoCivilModule {}
