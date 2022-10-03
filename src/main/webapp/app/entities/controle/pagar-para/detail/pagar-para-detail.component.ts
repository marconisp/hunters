import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPagarPara } from '../pagar-para.model';

@Component({
  selector: 'jhi-pagar-para-detail',
  templateUrl: './pagar-para-detail.component.html',
})
export class PagarParaDetailComponent implements OnInit {
  pagarPara: IPagarPara | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pagarPara }) => {
      this.pagarPara = pagarPara;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
