import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoContratacao } from '../tipo-contratacao.model';

@Component({
  selector: 'jhi-tipo-contratacao-detail',
  templateUrl: './tipo-contratacao-detail.component.html',
})
export class TipoContratacaoDetailComponent implements OnInit {
  tipoContratacao: ITipoContratacao | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoContratacao }) => {
      this.tipoContratacao = tipoContratacao;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
