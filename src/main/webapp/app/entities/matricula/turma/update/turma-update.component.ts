import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ITurma, Turma } from '../turma.model';
import { TurmaService } from '../service/turma.service';
import { IPeriodoDuracao } from 'app/entities/config/periodo-duracao/periodo-duracao.model';
import { PeriodoDuracaoService } from 'app/entities/config/periodo-duracao/service/periodo-duracao.service';

@Component({
  selector: 'jhi-turma-update',
  templateUrl: './turma-update.component.html',
})
export class TurmaUpdateComponent implements OnInit {
  isSaving = false;

  periodoDuracaosCollection: IPeriodoDuracao[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(50)]],
    ano: [null, [Validators.required]],
    periodoDuracao: [],
  });

  constructor(
    protected turmaService: TurmaService,
    protected periodoDuracaoService: PeriodoDuracaoService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ turma }) => {
      this.updateForm(turma);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const turma = this.createFromForm();
    if (turma.id !== undefined) {
      this.subscribeToSaveResponse(this.turmaService.update(turma));
    } else {
      this.subscribeToSaveResponse(this.turmaService.create(turma));
    }
  }

  trackPeriodoDuracaoById(_index: number, item: IPeriodoDuracao): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITurma>>): void {
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

  protected updateForm(turma: ITurma): void {
    this.editForm.patchValue({
      id: turma.id,
      nome: turma.nome,
      ano: turma.ano,
      periodoDuracao: turma.periodoDuracao,
    });

    this.periodoDuracaosCollection = this.periodoDuracaoService.addPeriodoDuracaoToCollectionIfMissing(
      this.periodoDuracaosCollection,
      turma.periodoDuracao
    );
  }

  protected loadRelationshipsOptions(): void {
    this.periodoDuracaoService
      .query({ filter: 'turma-is-null' })
      .pipe(map((res: HttpResponse<IPeriodoDuracao[]>) => res.body ?? []))
      .pipe(
        map((periodoDuracaos: IPeriodoDuracao[]) =>
          this.periodoDuracaoService.addPeriodoDuracaoToCollectionIfMissing(periodoDuracaos, this.editForm.get('periodoDuracao')!.value)
        )
      )
      .subscribe((periodoDuracaos: IPeriodoDuracao[]) => (this.periodoDuracaosCollection = periodoDuracaos));
  }

  protected createFromForm(): ITurma {
    return {
      ...new Turma(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      ano: this.editForm.get(['ano'])!.value,
      periodoDuracao: this.editForm.get(['periodoDuracao'])!.value,
    };
  }
}
