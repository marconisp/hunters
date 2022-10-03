import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoTransacao } from '../tipo-transacao.model';

@Component({
  selector: 'jhi-tipo-transacao-detail',
  templateUrl: './tipo-transacao-detail.component.html',
})
export class TipoTransacaoDetailComponent implements OnInit {
  tipoTransacao: ITipoTransacao | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoTransacao }) => {
      this.tipoTransacao = tipoTransacao;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
