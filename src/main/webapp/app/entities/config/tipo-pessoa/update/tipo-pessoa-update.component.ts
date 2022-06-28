import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITipoPessoa, TipoPessoa } from '../tipo-pessoa.model';
import { TipoPessoaService } from '../service/tipo-pessoa.service';

@Component({
  selector: 'jhi-tipo-pessoa-update',
  templateUrl: './tipo-pessoa-update.component.html',
})
export class TipoPessoaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    codigo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(20)]],
    nome: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    descricao: [null, [Validators.minLength(1), Validators.maxLength(100)]],
  });

  constructor(protected tipoPessoaService: TipoPessoaService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoPessoa }) => {
      this.updateForm(tipoPessoa);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoPessoa = this.createFromForm();
    if (tipoPessoa.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoPessoaService.update(tipoPessoa));
    } else {
      this.subscribeToSaveResponse(this.tipoPessoaService.create(tipoPessoa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoPessoa>>): void {
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

  protected updateForm(tipoPessoa: ITipoPessoa): void {
    this.editForm.patchValue({
      id: tipoPessoa.id,
      codigo: tipoPessoa.codigo,
      nome: tipoPessoa.nome,
      descricao: tipoPessoa.descricao,
    });
  }

  protected createFromForm(): ITipoPessoa {
    return {
      ...new TipoPessoa(),
      id: this.editForm.get(['id'])!.value,
      codigo: this.editForm.get(['codigo'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
    };
  }
}
