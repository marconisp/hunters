import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDiaSemana } from '../dia-semana.model';

@Component({
  selector: 'jhi-dia-semana-detail',
  templateUrl: './dia-semana-detail.component.html',
})
export class DiaSemanaDetailComponent implements OnInit {
  diaSemana: IDiaSemana | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ diaSemana }) => {
      this.diaSemana = diaSemana;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
