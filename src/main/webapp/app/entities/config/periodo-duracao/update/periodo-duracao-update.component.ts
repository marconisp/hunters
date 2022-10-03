import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPeriodoDuracao, PeriodoDuracao } from '../periodo-duracao.model';
import { PeriodoDuracaoService } from '../service/periodo-duracao.service';

@Component({
  selector: 'jhi-periodo-duracao-update',
  templateUrl: './periodo-duracao-update.component.html',
})
export class PeriodoDuracaoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(50)]],
    dataInicio: [null, [Validators.required]],
    dataFim: [null, [Validators.required]],
    horaInicio: [null, [Validators.maxLength(5)]],
    horaFim: [null, [Validators.maxLength(5)]],
    obs: [null, [Validators.maxLength(100)]],
  });

  constructor(
    protected periodoDuracaoService: PeriodoDuracaoService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ periodoDuracao }) => {
      this.updateForm(periodoDuracao);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const periodoDuracao = this.createFromForm();
    if (periodoDuracao.id !== undefined) {
      this.subscribeToSaveResponse(this.periodoDuracaoService.update(periodoDuracao));
    } else {
      this.subscribeToSaveResponse(this.periodoDuracaoService.create(periodoDuracao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPeriodoDuracao>>): void {
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

  protected updateForm(periodoDuracao: IPeriodoDuracao): void {
    this.editForm.patchValue({
      id: periodoDuracao.id,
      nome: periodoDuracao.nome,
      dataInicio: periodoDuracao.dataInicio,
      dataFim: periodoDuracao.dataFim,
      horaInicio: periodoDuracao.horaInicio,
      horaFim: periodoDuracao.horaFim,
      obs: periodoDuracao.obs,
    });
  }

  protected createFromForm(): IPeriodoDuracao {
    return {
      ...new PeriodoDuracao(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      dataInicio: this.editForm.get(['dataInicio'])!.value,
      dataFim: this.editForm.get(['dataFim'])!.value,
      horaInicio: this.editForm.get(['horaInicio'])!.value,
      horaFim: this.editForm.get(['horaFim'])!.value,
      obs: this.editForm.get(['obs'])!.value,
    };
  }
}
