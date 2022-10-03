import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPeriodoDuracao } from '../periodo-duracao.model';

@Component({
  selector: 'jhi-periodo-duracao-detail',
  templateUrl: './periodo-duracao-detail.component.html',
})
export class PeriodoDuracaoDetailComponent implements OnInit {
  periodoDuracao: IPeriodoDuracao | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ periodoDuracao }) => {
      this.periodoDuracao = periodoDuracao;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
