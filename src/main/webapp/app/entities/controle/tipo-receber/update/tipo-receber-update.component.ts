import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITipoReceber, TipoReceber } from '../tipo-receber.model';
import { TipoReceberService } from '../service/tipo-receber.service';

@Component({
  selector: 'jhi-tipo-receber-update',
  templateUrl: './tipo-receber-update.component.html',
})
export class TipoReceberUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(20)]],
  });

  constructor(protected tipoReceberService: TipoReceberService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoReceber }) => {
      this.updateForm(tipoReceber);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoReceber = this.createFromForm();
    if (tipoReceber.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoReceberService.update(tipoReceber));
    } else {
      this.subscribeToSaveResponse(this.tipoReceberService.create(tipoReceber));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoReceber>>): void {
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

  protected updateForm(tipoReceber: ITipoReceber): void {
    this.editForm.patchValue({
      id: tipoReceber.id,
      nome: tipoReceber.nome,
    });
  }

  protected createFromForm(): ITipoReceber {
    return {
      ...new TipoReceber(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
    };
  }
}
