import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IRaca, Raca } from '../raca.model';
import { RacaService } from '../service/raca.service';

@Component({
  selector: 'jhi-raca-update',
  templateUrl: './raca-update.component.html',
})
export class RacaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    codigo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(20)]],
    descricao: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
  });

  constructor(protected racaService: RacaService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ raca }) => {
      this.updateForm(raca);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const raca = this.createFromForm();
    if (raca.id !== undefined) {
      this.subscribeToSaveResponse(this.racaService.update(raca));
    } else {
      this.subscribeToSaveResponse(this.racaService.create(raca));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRaca>>): void {
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

  protected updateForm(raca: IRaca): void {
    this.editForm.patchValue({
      id: raca.id,
      codigo: raca.codigo,
      descricao: raca.descricao,
    });
  }

  protected createFromForm(): IRaca {
    return {
      ...new Raca(),
      id: this.editForm.get(['id'])!.value,
      codigo: this.editForm.get(['codigo'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
    };
  }
}
