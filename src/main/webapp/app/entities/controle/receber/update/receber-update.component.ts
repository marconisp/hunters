import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IReceber, Receber } from '../receber.model';
import { ReceberService } from '../service/receber.service';
import { ITipoReceber } from 'app/entities/controle/tipo-receber/tipo-receber.model';
import { TipoReceberService } from 'app/entities/controle/tipo-receber/service/tipo-receber.service';
import { IReceberDe } from 'app/entities/controle/receber-de/receber-de.model';
import { ReceberDeService } from 'app/entities/controle/receber-de/service/receber-de.service';
import { ITipoTransacao } from 'app/entities/controle/tipo-transacao/tipo-transacao.model';
import { TipoTransacaoService } from 'app/entities/controle/tipo-transacao/service/tipo-transacao.service';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';
import { DadosPessoaisService } from 'app/entities/user/dados-pessoais/service/dados-pessoais.service';
import { StatusContaReceber } from 'app/entities/enumerations/status-conta-receber.model';

@Component({
  selector: 'jhi-receber-update',
  templateUrl: './receber-update.component.html',
})
export class ReceberUpdateComponent implements OnInit {
  isSaving = false;
  statusContaReceberValues = Object.keys(StatusContaReceber);

  tipoRecebersCollection: ITipoReceber[] = [];
  receberDesCollection: IReceberDe[] = [];
  tipoTransacaosCollection: ITipoTransacao[] = [];
  dadosPessoaisSharedCollection: IDadosPessoais[] = [];

  editForm = this.fb.group({
    id: [],
    data: [null, [Validators.required]],
    valor: [null, [Validators.required]],
    status: [],
    obs: [null, [Validators.maxLength(100)]],
    tipoReceber: [],
    receberDe: [],
    tipoTransacao: [],
    dadosPessoais: [],
  });

  constructor(
    protected receberService: ReceberService,
    protected tipoReceberService: TipoReceberService,
    protected receberDeService: ReceberDeService,
    protected tipoTransacaoService: TipoTransacaoService,
    protected dadosPessoaisService: DadosPessoaisService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ receber }) => {
      this.updateForm(receber);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const receber = this.createFromForm();
    if (receber.id !== undefined) {
      this.subscribeToSaveResponse(this.receberService.update(receber));
    } else {
      this.subscribeToSaveResponse(this.receberService.create(receber));
    }
  }

  trackTipoReceberById(_index: number, item: ITipoReceber): number {
    return item.id!;
  }

  trackReceberDeById(_index: number, item: IReceberDe): number {
    return item.id!;
  }

  trackTipoTransacaoById(_index: number, item: ITipoTransacao): number {
    return item.id!;
  }

  trackDadosPessoaisById(_index: number, item: IDadosPessoais): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReceber>>): void {
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

  protected updateForm(receber: IReceber): void {
    this.editForm.patchValue({
      id: receber.id,
      data: receber.data,
      valor: receber.valor,
      status: receber.status,
      obs: receber.obs,
      tipoReceber: receber.tipoReceber,
      receberDe: receber.receberDe,
      tipoTransacao: receber.tipoTransacao,
      dadosPessoais: receber.dadosPessoais,
    });

    this.tipoRecebersCollection = this.tipoReceberService.addTipoReceberToCollectionIfMissing(
      this.tipoRecebersCollection,
      receber.tipoReceber
    );
    this.receberDesCollection = this.receberDeService.addReceberDeToCollectionIfMissing(this.receberDesCollection, receber.receberDe);
    this.tipoTransacaosCollection = this.tipoTransacaoService.addTipoTransacaoToCollectionIfMissing(
      this.tipoTransacaosCollection,
      receber.tipoTransacao
    );
    this.dadosPessoaisSharedCollection = this.dadosPessoaisService.addDadosPessoaisToCollectionIfMissing(
      this.dadosPessoaisSharedCollection,
      receber.dadosPessoais
    );
  }

  protected loadRelationshipsOptions(): void {
    this.tipoReceberService
      .query({ filter: 'receber-is-null' })
      .pipe(map((res: HttpResponse<ITipoReceber[]>) => res.body ?? []))
      .pipe(
        map((tipoRecebers: ITipoReceber[]) =>
          this.tipoReceberService.addTipoReceberToCollectionIfMissing(tipoRecebers, this.editForm.get('tipoReceber')!.value)
        )
      )
      .subscribe((tipoRecebers: ITipoReceber[]) => (this.tipoRecebersCollection = tipoRecebers));

    this.receberDeService
      .query({ filter: 'receber-is-null' })
      .pipe(map((res: HttpResponse<IReceberDe[]>) => res.body ?? []))
      .pipe(
        map((receberDes: IReceberDe[]) =>
          this.receberDeService.addReceberDeToCollectionIfMissing(receberDes, this.editForm.get('receberDe')!.value)
        )
      )
      .subscribe((receberDes: IReceberDe[]) => (this.receberDesCollection = receberDes));

    this.tipoTransacaoService
      .query({ filter: 'receber-is-null' })
      .pipe(map((res: HttpResponse<ITipoTransacao[]>) => res.body ?? []))
      .pipe(
        map((tipoTransacaos: ITipoTransacao[]) =>
          this.tipoTransacaoService.addTipoTransacaoToCollectionIfMissing(tipoTransacaos, this.editForm.get('tipoTransacao')!.value)
        )
      )
      .subscribe((tipoTransacaos: ITipoTransacao[]) => (this.tipoTransacaosCollection = tipoTransacaos));

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

  protected createFromForm(): IReceber {
    return {
      ...new Receber(),
      id: this.editForm.get(['id'])!.value,
      data: this.editForm.get(['data'])!.value,
      valor: this.editForm.get(['valor'])!.value,
      status: this.editForm.get(['status'])!.value,
      obs: this.editForm.get(['obs'])!.value,
      tipoReceber: this.editForm.get(['tipoReceber'])!.value,
      receberDe: this.editForm.get(['receberDe'])!.value,
      tipoTransacao: this.editForm.get(['tipoTransacao'])!.value,
      dadosPessoais: this.editForm.get(['dadosPessoais'])!.value,
    };
  }
}
