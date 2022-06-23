import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstadoCivil } from '../estado-civil.model';

@Component({
  selector: 'jhi-estado-civil-detail',
  templateUrl: './estado-civil-detail.component.html',
})
export class EstadoCivilDetailComponent implements OnInit {
  estadoCivil: IEstadoCivil | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estadoCivil }) => {
      this.estadoCivil = estadoCivil;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
