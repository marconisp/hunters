import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISubGrupoProduto, SubGrupoProduto } from '../sub-grupo-produto.model';
import { SubGrupoProdutoService } from '../service/sub-grupo-produto.service';
import { IGrupoProduto } from 'app/entities/controle/grupo-produto/grupo-produto.model';
import { GrupoProdutoService } from 'app/entities/controle/grupo-produto/service/grupo-produto.service';

@Component({
  selector: 'jhi-sub-grupo-produto-update',
  templateUrl: './sub-grupo-produto-update.component.html',
})
export class SubGrupoProdutoUpdateComponent implements OnInit {
  isSaving = false;

  grupoProdutosSharedCollection: IGrupoProduto[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(30)]],
    obs: [null, [Validators.maxLength(50)]],
    grupoProduto: [],
  });

  constructor(
    protected subGrupoProdutoService: SubGrupoProdutoService,
    protected grupoProdutoService: GrupoProdutoService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subGrupoProduto }) => {
      this.updateForm(subGrupoProduto);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const subGrupoProduto = this.createFromForm();
    if (subGrupoProduto.id !== undefined) {
      this.subscribeToSaveResponse(this.subGrupoProdutoService.update(subGrupoProduto));
    } else {
      this.subscribeToSaveResponse(this.subGrupoProdutoService.create(subGrupoProduto));
    }
  }

  trackGrupoProdutoById(_index: number, item: IGrupoProduto): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubGrupoProduto>>): void {
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

  protected updateForm(subGrupoProduto: ISubGrupoProduto): void {
    this.editForm.patchValue({
      id: subGrupoProduto.id,
      nome: subGrupoProduto.nome,
      obs: subGrupoProduto.obs,
      grupoProduto: subGrupoProduto.grupoProduto,
    });

    this.grupoProdutosSharedCollection = this.grupoProdutoService.addGrupoProdutoToCollectionIfMissing(
      this.grupoProdutosSharedCollection,
      subGrupoProduto.grupoProduto
    );
  }

  protected loadRelationshipsOptions(): void {
    this.grupoProdutoService
      .query()
      .pipe(map((res: HttpResponse<IGrupoProduto[]>) => res.body ?? []))
      .pipe(
        map((grupoProdutos: IGrupoProduto[]) =>
          this.grupoProdutoService.addGrupoProdutoToCollectionIfMissing(grupoProdutos, this.editForm.get('grupoProduto')!.value)
        )
      )
      .subscribe((grupoProdutos: IGrupoProduto[]) => (this.grupoProdutosSharedCollection = grupoProdutos));
  }

  protected createFromForm(): ISubGrupoProduto {
    return {
      ...new SubGrupoProduto(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      obs: this.editForm.get(['obs'])!.value,
      grupoProduto: this.editForm.get(['grupoProduto'])!.value,
    };
  }
}
