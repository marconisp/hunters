import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IEstadoCivil, EstadoCivil } from '../estado-civil.model';
import { EstadoCivilService } from '../service/estado-civil.service';

@Component({
  selector: 'jhi-estado-civil-update',
  templateUrl: './estado-civil-update.component.html',
})
export class EstadoCivilUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    codigo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(20)]],
    descricao: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
  });

  constructor(protected estadoCivilService: EstadoCivilService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estadoCivil }) => {
      this.updateForm(estadoCivil);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const estadoCivil = this.createFromForm();
    if (estadoCivil.id !== undefined) {
      this.subscribeToSaveResponse(this.estadoCivilService.update(estadoCivil));
    } else {
      this.subscribeToSaveResponse(this.estadoCivilService.create(estadoCivil));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstadoCivil>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(estadoCivil: IEstadoCivil): void {
    this.editForm.patchValue({
      id: estadoCivil.id,
      codigo: estadoCivil.codigo,
      descricao: estadoCivil.descricao,
    });
  }

  protected createFromForm(): IEstadoCivil {
    return {
      ...new EstadoCivil(),
      id: this.editForm.get(['id'])!.value,
      codigo: this.editForm.get(['codigo'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
    };
  }
}
