import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoPagar } from '../tipo-pagar.model';

@Component({
  selector: 'jhi-tipo-pagar-detail',
  templateUrl: './tipo-pagar-detail.component.html',
})
export class TipoPagarDetailComponent implements OnInit {
  tipoPagar: ITipoPagar | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoPagar }) => {
      this.tipoPagar = tipoPagar;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
