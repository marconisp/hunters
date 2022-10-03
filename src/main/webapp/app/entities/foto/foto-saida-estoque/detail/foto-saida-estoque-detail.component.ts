import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFotoSaidaEstoque } from '../foto-saida-estoque.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-foto-saida-estoque-detail',
  templateUrl: './foto-saida-estoque-detail.component.html',
})
export class FotoSaidaEstoqueDetailComponent implements OnInit {
  fotoSaidaEstoque: IFotoSaidaEstoque | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fotoSaidaEstoque }) => {
      this.fotoSaidaEstoque = fotoSaidaEstoque;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
