import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubGrupoProduto } from '../sub-grupo-produto.model';

@Component({
  selector: 'jhi-sub-grupo-produto-detail',
  templateUrl: './sub-grupo-produto-detail.component.html',
})
export class SubGrupoProdutoDetailComponent implements OnInit {
  subGrupoProduto: ISubGrupoProduto | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subGrupoProduto }) => {
      this.subGrupoProduto = subGrupoProduto;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
