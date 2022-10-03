import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISaidaEstoque } from '../saida-estoque.model';

@Component({
  selector: 'jhi-saida-estoque-detail',
  templateUrl: './saida-estoque-detail.component.html',
})
export class SaidaEstoqueDetailComponent implements OnInit {
  saidaEstoque: ISaidaEstoque | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ saidaEstoque }) => {
      this.saidaEstoque = saidaEstoque;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
