import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFotoEntradaEstoque } from '../foto-entrada-estoque.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-foto-entrada-estoque-detail',
  templateUrl: './foto-entrada-estoque-detail.component.html',
})
export class FotoEntradaEstoqueDetailComponent implements OnInit {
  fotoEntradaEstoque: IFotoEntradaEstoque | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fotoEntradaEstoque }) => {
      this.fotoEntradaEstoque = fotoEntradaEstoque;
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
