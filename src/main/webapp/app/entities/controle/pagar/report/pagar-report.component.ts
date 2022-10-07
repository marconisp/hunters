import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';
import * as fileSaver from 'file-saver';

import { PagarService } from '../service/pagar.service';
import { IFiltroPagar, FiltroPagar } from '../filtroPagar.model';

import { ITipoPagar } from 'app/entities/controle/tipo-pagar/tipo-pagar.model';
import { IPagarPara } from 'app/entities/controle/pagar-para/pagar-para.model';
import { StatusContaPagar } from 'app/entities/enumerations/status-conta-pagar.model';

import { TipoPagarService } from 'app/entities/controle/tipo-pagar/service/tipo-pagar.service';
import { PagarParaService } from 'app/entities/controle/pagar-para/service/pagar-para.service';
import { ITipoTransacao } from 'app/entities/controle/tipo-transacao/tipo-transacao.model';
import { TipoTransacaoService } from 'app/entities/controle/tipo-transacao/service/tipo-transacao.service';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';
import { DadosPessoaisService } from 'app/entities/user/dados-pessoais/service/dados-pessoais.service';

@Component({
  selector: 'jhi-pagar-report',
  templateUrl: './pagar-report.component.html',
})
export class PagarReportComponent implements OnInit {
  isPrinting = false;
  statusContaPagarValues = Object.keys(StatusContaPagar);
  tipoPagarCollection: ITipoPagar[] = [];
  pagarParaCollection: IPagarPara[] = [];
  tipoTransacaosCollection: ITipoTransacao[] = [];
  dadosPessoaisSharedCollection: IDadosPessoais[] = [];

  editForm = this.fb.group({
    dataInicio: [null, [Validators.required]],
    dataFim: [null, [Validators.required]],
    status: [],
    tipoPagar: [],
    pagarPara: [],
    tipoTransacao: [],
    dadosPessoais: [],
  });

  constructor(
    protected tipoPagarService: TipoPagarService,
    protected pagarParaService: PagarParaService,
    protected tipoTransacaoService: TipoTransacaoService,
    protected pagarService: PagarService,
    protected activatedRoute: ActivatedRoute,
    protected dadosPessoaisService: DadosPessoaisService,
    protected fb: FormBuilder
  ) {}

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

  ngOnInit(): void {
    this.isPrinting = false;
    this.loadRelationshipsOptions();
  }

  print(): void {
    this.isPrinting = true;
    const filtro: IFiltroPagar = new FiltroPagar();

    filtro.tipo = 'PRINT';
    filtro.dataInicio = this.editForm.get(['dataInicio'])!.value;
    filtro.dataFim = this.editForm.get(['dataFim'])!.value;
    filtro.status = this.editForm.get(['status'])!.value;
    filtro.transacao = this.editForm.get(['tipoTransacao'])!.value;
    filtro.tipoPagar = this.editForm.get(['tipoPagar'])!.value;
    filtro.pagarPara = this.editForm.get(['pagarPara'])!.value;
    filtro.dadosPessoais = this.editForm.get(['dadosPessoais'])!.value;

    this.pagarService.print(filtro).subscribe(res => {
      this.isPrinting = false;
      this.downloadFile(res.body, res.headers.get('content-type'), res.headers.get('content-disposition')?.split('filename=')[1], 'PRINT');
    });
  }

  downloadFile(data: any, contentType: any, filename: any, type: any): void {
    const blob = new Blob([data], { type: contentType.concat('; charset=utf-8') });
    if (type === 'PRINT') {
      const blobUrl = URL.createObjectURL(blob);
      const iframe = document.createElement('iframe');
      iframe.style.display = 'none';
      iframe.src = blobUrl;
      document.body.appendChild(iframe);
      if (iframe.contentWindow !== null) {
        iframe.contentWindow.print();
      }
    } else {
      fileSaver.saveAs(blob, filename);
      // const url = window.URL.createObjectURL(blob);
      // window.open(url, filename);
    }
  }

  previousState(): void {
    window.history.back();
  }

  protected subscribeToSaveResponse(result: Observable<any>): void {
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
    this.isPrinting = false;
  }

  protected loadRelationshipsOptions(): void {
    this.tipoPagarService
      .query({ filter: 'receber-is-null' })
      .pipe(map((res: HttpResponse<ITipoPagar[]>) => res.body ?? []))
      .pipe(
        map((tipoPagar: ITipoPagar[]) =>
          this.tipoPagarService.addTipoPagarToCollectionIfMissing(tipoPagar, this.editForm.get('tipoPagar')!.value)
        )
      )
      .subscribe((tipoPagar: ITipoPagar[]) => (this.tipoPagarCollection = tipoPagar));

    this.pagarParaService
      .query({ filter: 'receber-is-null' })
      .pipe(map((res: HttpResponse<IPagarPara[]>) => res.body ?? []))
      .pipe(
        map((pagarPara: IPagarPara[]) =>
          this.pagarParaService.addPagarParaToCollectionIfMissing(pagarPara, this.editForm.get('pagarPara')!.value)
        )
      )
      .subscribe((pagarPara: IPagarPara[]) => (this.pagarParaCollection = pagarPara));

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
}
