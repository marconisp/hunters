import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRaca } from '../raca.model';

@Component({
  selector: 'jhi-raca-detail',
  templateUrl: './raca-detail.component.html',
})
export class RacaDetailComponent implements OnInit {
  raca: IRaca | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ raca }) => {
      this.raca = raca;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
