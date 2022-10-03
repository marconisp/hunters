import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAvaliacaoEconomica } from '../avaliacao-economica.model';

@Component({
  selector: 'jhi-avaliacao-economica-detail',
  templateUrl: './avaliacao-economica-detail.component.html',
})
export class AvaliacaoEconomicaDetailComponent implements OnInit {
  avaliacaoEconomica: IAvaliacaoEconomica | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avaliacaoEconomica }) => {
      this.avaliacaoEconomica = avaliacaoEconomica;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
