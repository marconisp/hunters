import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICaracteristicasPsiquicas } from '../caracteristicas-psiquicas.model';

@Component({
  selector: 'jhi-caracteristicas-psiquicas-detail',
  templateUrl: './caracteristicas-psiquicas-detail.component.html',
})
export class CaracteristicasPsiquicasDetailComponent implements OnInit {
  caracteristicasPsiquicas: ICaracteristicasPsiquicas | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ caracteristicasPsiquicas }) => {
      this.caracteristicasPsiquicas = caracteristicasPsiquicas;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
