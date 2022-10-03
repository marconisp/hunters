import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAgendaColaborador } from '../agenda-colaborador.model';

@Component({
  selector: 'jhi-agenda-colaborador-detail',
  templateUrl: './agenda-colaborador-detail.component.html',
})
export class AgendaColaboradorDetailComponent implements OnInit {
  agendaColaborador: IAgendaColaborador | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ agendaColaborador }) => {
      this.agendaColaborador = agendaColaborador;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
