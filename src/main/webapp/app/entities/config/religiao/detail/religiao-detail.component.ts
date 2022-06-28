import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReligiao } from '../religiao.model';

@Component({
  selector: 'jhi-religiao-detail',
  templateUrl: './religiao-detail.component.html',
})
export class ReligiaoDetailComponent implements OnInit {
  religiao: IReligiao | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ religiao }) => {
      this.religiao = religiao;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
