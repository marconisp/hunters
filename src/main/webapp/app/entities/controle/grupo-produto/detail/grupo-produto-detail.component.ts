import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGrupoProduto } from '../grupo-produto.model';

@Component({
  selector: 'jhi-grupo-produto-detail',
  templateUrl: './grupo-produto-detail.component.html',
})
export class GrupoProdutoDetailComponent implements OnInit {
  grupoProduto: IGrupoProduto | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ grupoProduto }) => {
      this.grupoProduto = grupoProduto;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
