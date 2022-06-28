import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMensagem } from '../mensagem.model';

@Component({
  selector: 'jhi-mensagem-detail',
  templateUrl: './mensagem-detail.component.html',
})
export class MensagemDetailComponent implements OnInit {
  mensagem: IMensagem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mensagem }) => {
      this.mensagem = mensagem;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
