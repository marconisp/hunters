import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoMensagem } from '../tipo-mensagem.model';

@Component({
  selector: 'jhi-tipo-mensagem-detail',
  templateUrl: './tipo-mensagem-detail.component.html',
})
export class TipoMensagemDetailComponent implements OnInit {
  tipoMensagem: ITipoMensagem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoMensagem }) => {
      this.tipoMensagem = tipoMensagem;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
