import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAviso } from '../aviso.model';

@Component({
  selector: 'jhi-aviso-detail',
  templateUrl: './aviso-detail.component.html',
})
export class AvisoDetailComponent implements OnInit {
  aviso: IAviso | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aviso }) => {
      this.aviso = aviso;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
