import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IVacina, Vacina } from '../vacina.model';
import { VacinaService } from '../service/vacina.service';

@Component({
  selector: 'jhi-vacina-update',
  templateUrl: './vacina-update.component.html',
})
export class VacinaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(50)]],
    idade: [null, [Validators.maxLength(5)]],
    obs: [null, [Validators.maxLength(50)]],
  });

  constructor(protected vacinaService: VacinaService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vacina }) => {
      this.updateForm(vacina);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vacina = this.createFromForm();
    if (vacina.id !== undefined) {
      this.subscribeToSaveResponse(this.vacinaService.update(vacina));
    } else {
      this.subscribeToSaveResponse(this.vacinaService.create(vacina));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVacina>>): void {
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

  protected updateForm(vacina: IVacina): void {
    this.editForm.patchValue({
      id: vacina.id,
      nome: vacina.nome,
      idade: vacina.idade,
      obs: vacina.obs,
    });
  }

  protected createFromForm(): IVacina {
    return {
      ...new Vacina(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      idade: this.editForm.get(['idade'])!.value,
      obs: this.editForm.get(['obs'])!.value,
    };
  }
}
