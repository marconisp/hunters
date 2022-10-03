import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFotoExameMedico } from '../foto-exame-medico.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-foto-exame-medico-detail',
  templateUrl: './foto-exame-medico-detail.component.html',
})
export class FotoExameMedicoDetailComponent implements OnInit {
  fotoExameMedico: IFotoExameMedico | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fotoExameMedico }) => {
      this.fotoExameMedico = fotoExameMedico;
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
