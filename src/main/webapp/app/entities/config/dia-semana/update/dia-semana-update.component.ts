import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDiaSemana, DiaSemana } from '../dia-semana.model';
import { DiaSemanaService } from '../service/dia-semana.service';
import { IPeriodoDuracao } from 'app/entities/config/periodo-duracao/periodo-duracao.model';
import { PeriodoDuracaoService } from 'app/entities/config/periodo-duracao/service/periodo-duracao.service';

@Component({
  selector: 'jhi-dia-semana-update',
  templateUrl: './dia-semana-update.component.html',
})
export class DiaSemanaUpdateComponent implements OnInit {
  isSaving = false;

  periodoDuracaosSharedCollection: IPeriodoDuracao[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(20)]],
    obs: [null, [Validators.maxLength(100)]],
    periodoDuracao: [],
  });

  constructor(
    protected diaSemanaService: DiaSemanaService,
    protected periodoDuracaoService: PeriodoDuracaoService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ diaSemana }) => {
      this.updateForm(diaSemana);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const diaSemana = this.createFromForm();
    if (diaSemana.id !== undefined) {
      this.subscribeToSaveResponse(this.diaSemanaService.update(diaSemana));
    } else {
      this.subscribeToSaveResponse(this.diaSemanaService.create(diaSemana));
    }
  }

  trackPeriodoDuracaoById(_index: number, item: IPeriodoDuracao): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDiaSemana>>): void {
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

  protected updateForm(diaSemana: IDiaSemana): void {
    this.editForm.patchValue({
      id: diaSemana.id,
      nome: diaSemana.nome,
      obs: diaSemana.obs,
      periodoDuracao: diaSemana.periodoDuracao,
    });

    this.periodoDuracaosSharedCollection = this.periodoDuracaoService.addPeriodoDuracaoToCollectionIfMissing(
      this.periodoDuracaosSharedCollection,
      diaSemana.periodoDuracao
    );
  }

  protected loadRelationshipsOptions(): void {
    this.periodoDuracaoService
      .query()
      .pipe(map((res: HttpResponse<IPeriodoDuracao[]>) => res.body ?? []))
      .pipe(
        map((periodoDuracaos: IPeriodoDuracao[]) =>
          this.periodoDuracaoService.addPeriodoDuracaoToCollectionIfMissing(periodoDuracaos, this.editForm.get('periodoDuracao')!.value)
        )
      )
      .subscribe((periodoDuracaos: IPeriodoDuracao[]) => (this.periodoDuracaosSharedCollection = periodoDuracaos));
  }

  protected createFromForm(): IDiaSemana {
    return {
      ...new DiaSemana(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      obs: this.editForm.get(['obs'])!.value,
      periodoDuracao: this.editForm.get(['periodoDuracao'])!.value,
    };
  }
}
