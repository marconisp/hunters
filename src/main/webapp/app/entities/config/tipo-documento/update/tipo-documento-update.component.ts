import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITipoDocumento, TipoDocumento } from '../tipo-documento.model';
import { TipoDocumentoService } from '../service/tipo-documento.service';

@Component({
  selector: 'jhi-tipo-documento-update',
  templateUrl: './tipo-documento-update.component.html',
})
export class TipoDocumentoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    codigo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(20)]],
    nome: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    descricao: [null, [Validators.minLength(1), Validators.maxLength(100)]],
  });

  constructor(protected tipoDocumentoService: TipoDocumentoService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoDocumento }) => {
      this.updateForm(tipoDocumento);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoDocumento = this.createFromForm();
    if (tipoDocumento.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoDocumentoService.update(tipoDocumento));
    } else {
      this.subscribeToSaveResponse(this.tipoDocumentoService.create(tipoDocumento));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoDocumento>>): void {
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

  protected updateForm(tipoDocumento: ITipoDocumento): void {
    this.editForm.patchValue({
      id: tipoDocumento.id,
      codigo: tipoDocumento.codigo,
      nome: tipoDocumento.nome,
      descricao: tipoDocumento.descricao,
    });
  }

  protected createFromForm(): ITipoDocumento {
    return {
      ...new TipoDocumento(),
      id: this.editForm.get(['id'])!.value,
      codigo: this.editForm.get(['codigo'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
    };
  }
}
