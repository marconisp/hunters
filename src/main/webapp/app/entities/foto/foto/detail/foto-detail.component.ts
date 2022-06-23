import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFoto } from '../foto.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-foto-detail',
  templateUrl: './foto-detail.component.html',
})
export class FotoDetailComponent implements OnInit {
  foto: IFoto | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ foto }) => {
      this.foto = foto;
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
