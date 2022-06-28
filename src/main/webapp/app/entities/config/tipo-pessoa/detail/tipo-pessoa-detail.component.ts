import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoPessoa } from '../tipo-pessoa.model';

@Component({
  selector: 'jhi-tipo-pessoa-detail',
  templateUrl: './tipo-pessoa-detail.component.html',
})
export class TipoPessoaDetailComponent implements OnInit {
  tipoPessoa: ITipoPessoa | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoPessoa }) => {
      this.tipoPessoa = tipoPessoa;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
