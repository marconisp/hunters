import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAviso, Aviso } from '../aviso.model';
import { AvisoService } from '../service/aviso.service';
import { IDadosPessoais, DadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';

@Component({
  selector: 'jhi-aviso-update',
  templateUrl: './aviso-update.component.html',
})
export class AvisoUpdateComponent implements OnInit {
  isSaving = false;
  dadosPessoais?: IDadosPessoais;

  editForm = this.fb.group({
    id: [],
    data: [null, [Validators.required]],
    titulo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(40)]],
    conteudo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(1000)]],
  });

  constructor(protected avisoService: AvisoService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aviso, dadosPessoais }) => {
      this.updateForm(aviso);
      this.dadosPessoais = dadosPessoais ?? new DadosPessoais();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const aviso = this.createFromForm();
    aviso.dadosPessoais = this.dadosPessoais;
    if (aviso.id !== undefined) {
      this.subscribeToSaveResponse(this.avisoService.update(aviso));
    } else {
      this.subscribeToSaveResponse(this.avisoService.create(aviso));
    }
  }

  trackDadosPessoaisById(_index: number, item: IDadosPessoais): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAviso>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(aviso: IAviso): void {
    this.editForm.patchValue({
      id: aviso.id,
      data: aviso.data,
      titulo: aviso.titulo,
      conteudo: aviso.conteudo,
    });
  }

  protected createFromForm(): IAviso {
    return {
      ...new Aviso(),
      id: this.editForm.get(['id'])!.value,
      data: this.editForm.get(['data'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      conteudo: this.editForm.get(['conteudo'])!.value,
    };
  }
}
