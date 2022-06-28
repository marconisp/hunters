import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDadosPessoais } from '../dados-pessoais.model';

@Component({
  selector: 'jhi-dados-pessoais-detail',
  templateUrl: './dados-pessoais-detail.component.html',
})
export class DadosPessoaisDetailComponent implements OnInit {
  dadosPessoais: IDadosPessoais | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dadosPessoais }) => {
      this.dadosPessoais = dadosPessoais;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
