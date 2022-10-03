import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReceberDe } from '../receber-de.model';

@Component({
  selector: 'jhi-receber-de-detail',
  templateUrl: './receber-de-detail.component.html',
})
export class ReceberDeDetailComponent implements OnInit {
  receberDe: IReceberDe | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ receberDe }) => {
      this.receberDe = receberDe;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
