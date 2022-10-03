import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAgendaColaborador, AgendaColaborador } from '../agenda-colaborador.model';
import { AgendaColaboradorService } from '../service/agenda-colaborador.service';
import { IPeriodoDuracao } from 'app/entities/config/periodo-duracao/periodo-duracao.model';
import { PeriodoDuracaoService } from 'app/entities/config/periodo-duracao/service/periodo-duracao.service';
import { IColaborador } from 'app/entities/user/colaborador/colaborador.model';
import { ColaboradorService } from 'app/entities/user/colaborador/service/colaborador.service';

@Component({
  selector: 'jhi-agenda-colaborador-update',
  templateUrl: './agenda-colaborador-update.component.html',
})
export class AgendaColaboradorUpdateComponent implements OnInit {
  isSaving = false;

  periodoDuracaosCollection: IPeriodoDuracao[] = [];
  colaboradorsSharedCollection: IColaborador[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(50)]],
    obs: [null, [Validators.maxLength(100)]],
    periodoDuracao: [],
    colaborador: [],
  });

  constructor(
    protected agendaColaboradorService: AgendaColaboradorService,
    protected periodoDuracaoService: PeriodoDuracaoService,
    protected colaboradorService: ColaboradorService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ agendaColaborador }) => {
      this.updateForm(agendaColaborador);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const agendaColaborador = this.createFromForm();
    if (agendaColaborador.id !== undefined) {
      this.subscribeToSaveResponse(this.agendaColaboradorService.update(agendaColaborador));
    } else {
      this.subscribeToSaveResponse(this.agendaColaboradorService.create(agendaColaborador));
    }
  }

  trackPeriodoDuracaoById(_index: number, item: IPeriodoDuracao): number {
    return item.id!;
  }

  trackColaboradorById(_index: number, item: IColaborador): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgendaColaborador>>): void {
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

  protected updateForm(agendaColaborador: IAgendaColaborador): void {
    this.editForm.patchValue({
      id: agendaColaborador.id,
      nome: agendaColaborador.nome,
      obs: agendaColaborador.obs,
      periodoDuracao: agendaColaborador.periodoDuracao,
      colaborador: agendaColaborador.colaborador,
    });

    this.periodoDuracaosCollection = this.periodoDuracaoService.addPeriodoDuracaoToCollectionIfMissing(
      this.periodoDuracaosCollection,
      agendaColaborador.periodoDuracao
    );
    this.colaboradorsSharedCollection = this.colaboradorService.addColaboradorToCollectionIfMissing(
      this.colaboradorsSharedCollection,
      agendaColaborador.colaborador
    );
  }

  protected loadRelationshipsOptions(): void {
    this.periodoDuracaoService
      .query({ filter: 'agendacolaborador-is-null' })
      .pipe(map((res: HttpResponse<IPeriodoDuracao[]>) => res.body ?? []))
      .pipe(
        map((periodoDuracaos: IPeriodoDuracao[]) =>
          this.periodoDuracaoService.addPeriodoDuracaoToCollectionIfMissing(periodoDuracaos, this.editForm.get('periodoDuracao')!.value)
        )
      )
      .subscribe((periodoDuracaos: IPeriodoDuracao[]) => (this.periodoDuracaosCollection = periodoDuracaos));

    this.colaboradorService
      .query()
      .pipe(map((res: HttpResponse<IColaborador[]>) => res.body ?? []))
      .pipe(
        map((colaboradors: IColaborador[]) =>
          this.colaboradorService.addColaboradorToCollectionIfMissing(colaboradors, this.editForm.get('colaborador')!.value)
        )
      )
      .subscribe((colaboradors: IColaborador[]) => (this.colaboradorsSharedCollection = colaboradors));
  }

  protected createFromForm(): IAgendaColaborador {
    return {
      ...new AgendaColaborador(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      obs: this.editForm.get(['obs'])!.value,
      periodoDuracao: this.editForm.get(['periodoDuracao'])!.value,
      colaborador: this.editForm.get(['colaborador'])!.value,
    };
  }
}
