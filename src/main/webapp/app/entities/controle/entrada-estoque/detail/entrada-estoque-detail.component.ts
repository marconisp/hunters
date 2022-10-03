import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEntradaEstoque } from '../entrada-estoque.model';

@Component({
  selector: 'jhi-entrada-estoque-detail',
  templateUrl: './entrada-estoque-detail.component.html',
})
export class EntradaEstoqueDetailComponent implements OnInit {
  entradaEstoque: IEntradaEstoque | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entradaEstoque }) => {
      this.entradaEstoque = entradaEstoque;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
