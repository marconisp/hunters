import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPagar, Pagar } from '../pagar.model';
import { PagarService } from '../service/pagar.service';
import { ITipoPagar } from 'app/entities/controle/tipo-pagar/tipo-pagar.model';
import { TipoPagarService } from 'app/entities/controle/tipo-pagar/service/tipo-pagar.service';
import { IPagarPara } from 'app/entities/controle/pagar-para/pagar-para.model';
import { PagarParaService } from 'app/entities/controle/pagar-para/service/pagar-para.service';
import { ITipoTransacao } from 'app/entities/controle/tipo-transacao/tipo-transacao.model';
import { TipoTransacaoService } from 'app/entities/controle/tipo-transacao/service/tipo-transacao.service';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';
import { DadosPessoaisService } from 'app/entities/user/dados-pessoais/service/dados-pessoais.service';
import { StatusContaPagar } from 'app/entities/enumerations/status-conta-pagar.model';

@Component({
  selector: 'jhi-pagar-update',
  templateUrl: './pagar-update.component.html',
})
export class PagarUpdateComponent implements OnInit {
  isSaving = false;
  statusContaPagarValues = Object.keys(StatusContaPagar);

  tipoPagarsCollection: ITipoPagar[] = [];
  pagarParasCollection: IPagarPara[] = [];
  tipoTransacaosCollection: ITipoTransacao[] = [];
  dadosPessoaisSharedCollection: IDadosPessoais[] = [];

  editForm = this.fb.group({
    id: [],
    data: [null, [Validators.required]],
    valor: [null, [Validators.required]],
    status: [],
    obs: [null, [Validators.maxLength(100)]],
    tipoPagar: [],
    pagarPara: [],
    tipoTransacao: [],
    dadosPessoais: [],
  });

  constructor(
    protected pagarService: PagarService,
    protected tipoPagarService: TipoPagarService,
    protected pagarParaService: PagarParaService,
    protected tipoTransacaoService: TipoTransacaoService,
    protected dadosPessoaisService: DadosPessoaisService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pagar }) => {
      this.updateForm(pagar);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pagar = this.createFromForm();
    if (pagar.id !== undefined) {
      this.subscribeToSaveResponse(this.pagarService.update(pagar));
    } else {
      this.subscribeToSaveResponse(this.pagarService.create(pagar));
    }
  }

  trackTipoPagarById(_index: number, item: ITipoPagar): number {
    return item.id!;
  }

  trackPagarParaById(_index: number, item: IPagarPara): number {
    return item.id!;
  }

  trackTipoTransacaoById(_index: number, item: ITipoTransacao): number {
    return item.id!;
  }

  trackDadosPessoaisById(_index: number, item: IDadosPessoais): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPagar>>): void {
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

  protected updateForm(pagar: IPagar): void {
    this.editForm.patchValue({
      id: pagar.id,
      data: pagar.data,
      valor: pagar.valor,
      status: pagar.status,
      obs: pagar.obs,
      tipoPagar: pagar.tipoPagar,
      pagarPara: pagar.pagarPara,
      tipoTransacao: pagar.tipoTransacao,
      dadosPessoais: pagar.dadosPessoais,
    });

    this.tipoPagarsCollection = this.tipoPagarService.addTipoPagarToCollectionIfMissing(this.tipoPagarsCollection, pagar.tipoPagar);
    this.pagarParasCollection = this.pagarParaService.addPagarParaToCollectionIfMissing(this.pagarParasCollection, pagar.pagarPara);
    this.tipoTransacaosCollection = this.tipoTransacaoService.addTipoTransacaoToCollectionIfMissing(
      this.tipoTransacaosCollection,
      pagar.tipoTransacao
    );
    this.dadosPessoaisSharedCollection = this.dadosPessoaisService.addDadosPessoaisToCollectionIfMissing(
      this.dadosPessoaisSharedCollection,
      pagar.dadosPessoais
    );
  }

  protected loadRelationshipsOptions(): void {
    this.tipoPagarService
      .query({ filter: 'pagar-is-null' })
      .pipe(map((res: HttpResponse<ITipoPagar[]>) => res.body ?? []))
      .pipe(
        map((tipoPagars: ITipoPagar[]) =>
          this.tipoPagarService.addTipoPagarToCollectionIfMissing(tipoPagars, this.editForm.get('tipoPagar')!.value)
        )
      )
      .subscribe((tipoPagars: ITipoPagar[]) => (this.tipoPagarsCollection = tipoPagars));

    this.pagarParaService
      .query({ filter: 'pagar-is-null' })
      .pipe(map((res: HttpResponse<IPagarPara[]>) => res.body ?? []))
      .pipe(
        map((pagarParas: IPagarPara[]) =>
          this.pagarParaService.addPagarParaToCollectionIfMissing(pagarParas, this.editForm.get('pagarPara')!.value)
        )
      )
      .subscribe((pagarParas: IPagarPara[]) => (this.pagarParasCollection = pagarParas));

    this.tipoTransacaoService
      .query({ filter: 'pagar-is-null' })
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

  protected createFromForm(): IPagar {
    return {
      ...new Pagar(),
      id: this.editForm.get(['id'])!.value,
      data: this.editForm.get(['data'])!.value,
      valor: this.editForm.get(['valor'])!.value,
      status: this.editForm.get(['status'])!.value,
      obs: this.editForm.get(['obs'])!.value,
      tipoPagar: this.editForm.get(['tipoPagar'])!.value,
      pagarPara: this.editForm.get(['pagarPara'])!.value,
      tipoTransacao: this.editForm.get(['tipoTransacao'])!.value,
      dadosPessoais: this.editForm.get(['dadosPessoais'])!.value,
    };
  }
}
