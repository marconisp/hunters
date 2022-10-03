import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IColaborador } from '../colaborador.model';

@Component({
  selector: 'jhi-colaborador-detail',
  templateUrl: './colaborador-detail.component.html',
})
export class ColaboradorDetailComponent implements OnInit {
  colaborador: IColaborador | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ colaborador }) => {
      this.colaborador = colaborador;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
