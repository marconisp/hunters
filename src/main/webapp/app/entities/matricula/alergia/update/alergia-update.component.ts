import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAlergia, Alergia } from '../alergia.model';
import { AlergiaService } from '../service/alergia.service';

@Component({
  selector: 'jhi-alergia-update',
  templateUrl: './alergia-update.component.html',
})
export class AlergiaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(50)]],
    sintoma: [null, [Validators.maxLength(150)]],
    precaucoes: [null, [Validators.maxLength(150)]],
    socorro: [null, [Validators.maxLength(150)]],
    obs: [null, [Validators.maxLength(50)]],
  });

  constructor(protected alergiaService: AlergiaService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alergia }) => {
      this.updateForm(alergia);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const alergia = this.createFromForm();
    if (alergia.id !== undefined) {
      this.subscribeToSaveResponse(this.alergiaService.update(alergia));
    } else {
      this.subscribeToSaveResponse(this.alergiaService.create(alergia));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlergia>>): void {
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

  protected updateForm(alergia: IAlergia): void {
    this.editForm.patchValue({
      id: alergia.id,
      nome: alergia.nome,
      sintoma: alergia.sintoma,
      precaucoes: alergia.precaucoes,
      socorro: alergia.socorro,
      obs: alergia.obs,
    });
  }

  protected createFromForm(): IAlergia {
    return {
      ...new Alergia(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      sintoma: this.editForm.get(['sintoma'])!.value,
      precaucoes: this.editForm.get(['precaucoes'])!.value,
      socorro: this.editForm.get(['socorro'])!.value,
      obs: this.editForm.get(['obs'])!.value,
    };
  }
}
