import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';
import * as fileSaver from 'file-saver';

import { ReceberService } from '../service/receber.service';
import { IFiltroReceber, FiltroReceber } from '../FiltroReceber.model';

import { ITipoReceber } from 'app/entities/controle/tipo-receber/tipo-receber.model';
import { TipoReceberService } from 'app/entities/controle/tipo-receber/service/tipo-receber.service';
import { IReceberDe } from 'app/entities/controle/receber-de/receber-de.model';
import { ReceberDeService } from 'app/entities/controle/receber-de/service/receber-de.service';
import { ITipoTransacao } from 'app/entities/controle/tipo-transacao/tipo-transacao.model';
import { TipoTransacaoService } from 'app/entities/controle/tipo-transacao/service/tipo-transacao.service';
import { StatusContaReceber } from 'app/entities/enumerations/status-conta-receber.model';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';
import { DadosPessoaisService } from 'app/entities/user/dados-pessoais/service/dados-pessoais.service';

@Component({
  selector: 'jhi-receber-report',
  templateUrl: './receber-report.component.html',
})
export class ReceberReportComponent implements OnInit {
  isPrinting = false;
  statusContaReceberValues = Object.keys(StatusContaReceber);
  tipoRecebersCollection: ITipoReceber[] = [];
  receberDesCollection: IReceberDe[] = [];
  tipoTransacaosCollection: ITipoTransacao[] = [];
  dadosPessoaisSharedCollection: IDadosPessoais[] = [];

  editForm = this.fb.group({
    dataInicio: [null, [Validators.required]],
    dataFim: [null, [Validators.required]],
    status: [],
    tipoReceber: [],
    receberDe: [],
    tipoTransacao: [],
    dadosPessoais: [],
  });

  constructor(
    protected tipoReceberService: TipoReceberService,
    protected receberDeService: ReceberDeService,
    protected tipoTransacaoService: TipoTransacaoService,
    protected receberService: ReceberService,
    protected activatedRoute: ActivatedRoute,
    protected dadosPessoaisService: DadosPessoaisService,
    protected fb: FormBuilder
  ) {}

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

  ngOnInit(): void {
    this.isPrinting = false;
    this.loadRelationshipsOptions();
  }

  print(): void {
    this.isPrinting = true;
    const filtro: IFiltroReceber = new FiltroReceber();

    filtro.tipo = 'PRINT';
    filtro.dataInicio = this.editForm.get(['dataInicio'])!.value;
    filtro.dataFim = this.editForm.get(['dataFim'])!.value;
    filtro.status = this.editForm.get(['status'])!.value;
    filtro.transacao = this.editForm.get(['tipoTransacao'])!.value;
    filtro.tipoReceber = this.editForm.get(['tipoReceber'])!.value;
    filtro.receberDe = this.editForm.get(['receberDe'])!.value;
    filtro.dadosPessoais = this.editForm.get(['dadosPessoais'])!.value;

    this.receberService.print(filtro).subscribe(res => {
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
}
