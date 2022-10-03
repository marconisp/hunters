import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITipoTransacao, TipoTransacao } from '../tipo-transacao.model';
import { TipoTransacaoService } from '../service/tipo-transacao.service';

@Component({
  selector: 'jhi-tipo-transacao-update',
  templateUrl: './tipo-transacao-update.component.html',
})
export class TipoTransacaoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(20)]],
  });

  constructor(protected tipoTransacaoService: TipoTransacaoService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoTransacao }) => {
      this.updateForm(tipoTransacao);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoTransacao = this.createFromForm();
    if (tipoTransacao.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoTransacaoService.update(tipoTransacao));
    } else {
      this.subscribeToSaveResponse(this.tipoTransacaoService.create(tipoTransacao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoTransacao>>): void {
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

  protected updateForm(tipoTransacao: ITipoTransacao): void {
    this.editForm.patchValue({
      id: tipoTransacao.id,
      nome: tipoTransacao.nome,
    });
  }

  protected createFromForm(): ITipoTransacao {
    return {
      ...new TipoTransacao(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
    };
  }
}
