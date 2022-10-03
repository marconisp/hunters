import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPagar } from '../pagar.model';

@Component({
  selector: 'jhi-pagar-detail',
  templateUrl: './pagar-detail.component.html',
})
export class PagarDetailComponent implements OnInit {
  pagar: IPagar | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pagar }) => {
      this.pagar = pagar;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
