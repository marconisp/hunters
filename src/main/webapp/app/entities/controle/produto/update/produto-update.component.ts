import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IProduto, Produto } from '../produto.model';
import { ProdutoService } from '../service/produto.service';

@Component({
  selector: 'jhi-produto-update',
  templateUrl: './produto-update.component.html',
})
export class ProdutoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(50)]],
    descricao: [null, [Validators.maxLength(100)]],
    unidade: [null, [Validators.maxLength(20)]],
    peso: [null, [Validators.maxLength(20)]],
  });

  constructor(protected produtoService: ProdutoService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ produto }) => {
      this.updateForm(produto);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const produto = this.createFromForm();
    if (produto.id !== undefined) {
      this.subscribeToSaveResponse(this.produtoService.update(produto));
    } else {
      this.subscribeToSaveResponse(this.produtoService.create(produto));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduto>>): void {
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

  protected updateForm(produto: IProduto): void {
    this.editForm.patchValue({
      id: produto.id,
      nome: produto.nome,
      descricao: produto.descricao,
      unidade: produto.unidade,
      peso: produto.peso,
    });
  }

  protected createFromForm(): IProduto {
    return {
      ...new Produto(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      unidade: this.editForm.get(['unidade'])!.value,
      peso: this.editForm.get(['peso'])!.value,
    };
  }
}
