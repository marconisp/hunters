import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEvento, Evento } from '../evento.model';
import { EventoService } from '../service/evento.service';
import { IPeriodoDuracao } from 'app/entities/config/periodo-duracao/periodo-duracao.model';
import { PeriodoDuracaoService } from 'app/entities/config/periodo-duracao/service/periodo-duracao.service';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';
import { DadosPessoaisService } from 'app/entities/user/dados-pessoais/service/dados-pessoais.service';

@Component({
  selector: 'jhi-evento-update',
  templateUrl: './evento-update.component.html',
})
export class EventoUpdateComponent implements OnInit {
  isSaving = false;

  periodoDuracaosCollection: IPeriodoDuracao[] = [];
  dadosPessoaisSharedCollection: IDadosPessoais[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(50)]],
    descricao: [null, [Validators.maxLength(100)]],
    ativo: [],
    obs: [null, [Validators.maxLength(100)]],
    periodoDuracao: [],
    dadosPessoais: [],
  });

  constructor(
    protected eventoService: EventoService,
    protected periodoDuracaoService: PeriodoDuracaoService,
    protected dadosPessoaisService: DadosPessoaisService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ evento }) => {
      this.updateForm(evento);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const evento = this.createFromForm();
    if (evento.id !== undefined) {
      this.subscribeToSaveResponse(this.eventoService.update(evento));
    } else {
      this.subscribeToSaveResponse(this.eventoService.create(evento));
    }
  }

  trackPeriodoDuracaoById(_index: number, item: IPeriodoDuracao): number {
    return item.id!;
  }

  trackDadosPessoaisById(_index: number, item: IDadosPessoais): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvento>>): void {
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

  protected updateForm(evento: IEvento): void {
    this.editForm.patchValue({
      id: evento.id,
      nome: evento.nome,
      descricao: evento.descricao,
      ativo: evento.ativo,
      obs: evento.obs,
      periodoDuracao: evento.periodoDuracao,
      dadosPessoais: evento.dadosPessoais,
    });

    this.periodoDuracaosCollection = this.periodoDuracaoService.addPeriodoDuracaoToCollectionIfMissing(
      this.periodoDuracaosCollection,
      evento.periodoDuracao
    );
    this.dadosPessoaisSharedCollection = this.dadosPessoaisService.addDadosPessoaisToCollectionIfMissing(
      this.dadosPessoaisSharedCollection,
      evento.dadosPessoais
    );
  }

  protected loadRelationshipsOptions(): void {
    this.periodoDuracaoService
      .query({ filter: 'evento-is-null' })
      .pipe(map((res: HttpResponse<IPeriodoDuracao[]>) => res.body ?? []))
      .pipe(
        map((periodoDuracaos: IPeriodoDuracao[]) =>
          this.periodoDuracaoService.addPeriodoDuracaoToCollectionIfMissing(periodoDuracaos, this.editForm.get('periodoDuracao')!.value)
        )
      )
      .subscribe((periodoDuracaos: IPeriodoDuracao[]) => (this.periodoDuracaosCollection = periodoDuracaos));

    this.dadosPessoaisService
      .query()
      .pipe(map((res: HttpResponse<IDadosPessoais[]>) => res.body ?? []))
      .pipe(
        map((dadosPessoais: IDadosPessoais[]) =>
          this.dadosPessoaisService.addDadosPessoaisToCollectionIfMissing(dadosPessoais, this.editForm.get('dadosPessoais')!.value)
        )
      )
      .subscribe((dadosPessoais: IDadosPessoais[]) => (this.dadosPessoaisSharedCollection = dadosPessoais));
  }

  protected createFromForm(): IEvento {
    return {
      ...new Evento(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      ativo: this.editForm.get(['ativo'])!.value,
      obs: this.editForm.get(['obs'])!.value,
      periodoDuracao: this.editForm.get(['periodoDuracao'])!.value,
      dadosPessoais: this.editForm.get(['dadosPessoais'])!.value,
    };
  }
}
