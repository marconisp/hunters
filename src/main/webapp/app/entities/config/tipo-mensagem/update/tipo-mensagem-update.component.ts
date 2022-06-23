import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITipoMensagem, TipoMensagem } from '../tipo-mensagem.model';
import { TipoMensagemService } from '../service/tipo-mensagem.service';

@Component({
  selector: 'jhi-tipo-mensagem-update',
  templateUrl: './tipo-mensagem-update.component.html',
})
export class TipoMensagemUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    codigo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(20)]],
    nome: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    descricao: [null, [Validators.minLength(1), Validators.maxLength(100)]],
  });

  constructor(protected tipoMensagemService: TipoMensagemService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoMensagem }) => {
      this.updateForm(tipoMensagem);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoMensagem = this.createFromForm();
    if (tipoMensagem.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoMensagemService.update(tipoMensagem));
    } else {
      this.subscribeToSaveResponse(this.tipoMensagemService.create(tipoMensagem));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoMensagem>>): void {
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

  protected updateForm(tipoMensagem: ITipoMensagem): void {
    this.editForm.patchValue({
      id: tipoMensagem.id,
      codigo: tipoMensagem.codigo,
      nome: tipoMensagem.nome,
      descricao: tipoMensagem.descricao,
    });
  }

  protected createFromForm(): ITipoMensagem {
    return {
      ...new TipoMensagem(),
      id: this.editForm.get(['id'])!.value,
      codigo: this.editForm.get(['codigo'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
    };
  }
}
