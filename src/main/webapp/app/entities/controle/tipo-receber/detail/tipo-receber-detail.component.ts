import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoReceber } from '../tipo-receber.model';

@Component({
  selector: 'jhi-tipo-receber-detail',
  templateUrl: './tipo-receber-detail.component.html',
})
export class TipoReceberDetailComponent implements OnInit {
  tipoReceber: ITipoReceber | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoReceber }) => {
      this.tipoReceber = tipoReceber;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
