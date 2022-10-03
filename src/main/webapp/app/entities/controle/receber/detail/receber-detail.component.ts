import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReceber } from '../receber.model';

@Component({
  selector: 'jhi-receber-detail',
  templateUrl: './receber-detail.component.html',
})
export class ReceberDetailComponent implements OnInit {
  receber: IReceber | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ receber }) => {
      this.receber = receber;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
