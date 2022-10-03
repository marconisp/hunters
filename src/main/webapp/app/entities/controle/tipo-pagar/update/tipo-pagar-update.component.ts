import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITipoPagar, TipoPagar } from '../tipo-pagar.model';
import { TipoPagarService } from '../service/tipo-pagar.service';

@Component({
  selector: 'jhi-tipo-pagar-update',
  templateUrl: './tipo-pagar-update.component.html',
})
export class TipoPagarUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(20)]],
  });

  constructor(protected tipoPagarService: TipoPagarService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoPagar }) => {
      this.updateForm(tipoPagar);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoPagar = this.createFromForm();
    if (tipoPagar.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoPagarService.update(tipoPagar));
    } else {
      this.subscribeToSaveResponse(this.tipoPagarService.create(tipoPagar));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoPagar>>): void {
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

  protected updateForm(tipoPagar: ITipoPagar): void {
    this.editForm.patchValue({
      id: tipoPagar.id,
      nome: tipoPagar.nome,
    });
  }

  protected createFromForm(): ITipoPagar {
    return {
      ...new TipoPagar(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
    };
  }
}
