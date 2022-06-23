import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFotoIcon } from '../foto-icon.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-foto-icon-detail',
  templateUrl: './foto-icon-detail.component.html',
})
export class FotoIconDetailComponent implements OnInit {
  fotoIcon: IFotoIcon | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fotoIcon }) => {
      this.fotoIcon = fotoIcon;
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
