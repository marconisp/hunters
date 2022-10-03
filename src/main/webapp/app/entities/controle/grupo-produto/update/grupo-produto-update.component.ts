import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IGrupoProduto, GrupoProduto } from '../grupo-produto.model';
import { GrupoProdutoService } from '../service/grupo-produto.service';

@Component({
  selector: 'jhi-grupo-produto-update',
  templateUrl: './grupo-produto-update.component.html',
})
export class GrupoProdutoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(30)]],
    obs: [null, [Validators.maxLength(50)]],
  });

  constructor(protected grupoProdutoService: GrupoProdutoService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ grupoProduto }) => {
      this.updateForm(grupoProduto);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const grupoProduto = this.createFromForm();
    if (grupoProduto.id !== undefined) {
      this.subscribeToSaveResponse(this.grupoProdutoService.update(grupoProduto));
    } else {
      this.subscribeToSaveResponse(this.grupoProdutoService.create(grupoProduto));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGrupoProduto>>): void {
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

  protected updateForm(grupoProduto: IGrupoProduto): void {
    this.editForm.patchValue({
      id: grupoProduto.id,
      nome: grupoProduto.nome,
      obs: grupoProduto.obs,
    });
  }

  protected createFromForm(): IGrupoProduto {
    return {
      ...new GrupoProduto(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      obs: this.editForm.get(['obs'])!.value,
    };
  }
}
