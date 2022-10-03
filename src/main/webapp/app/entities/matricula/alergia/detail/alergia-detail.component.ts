import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAlergia } from '../alergia.model';

@Component({
  selector: 'jhi-alergia-detail',
  templateUrl: './alergia-detail.component.html',
})
export class AlergiaDetailComponent implements OnInit {
  alergia: IAlergia | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alergia }) => {
      this.alergia = alergia;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
