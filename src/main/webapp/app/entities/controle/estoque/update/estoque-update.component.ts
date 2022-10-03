import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEstoque, Estoque } from '../estoque.model';
import { EstoqueService } from '../service/estoque.service';
import { IProduto } from 'app/entities/controle/produto/produto.model';
import { ProdutoService } from 'app/entities/controle/produto/service/produto.service';
import { IGrupoProduto } from 'app/entities/controle/grupo-produto/grupo-produto.model';
import { GrupoProdutoService } from 'app/entities/controle/grupo-produto/service/grupo-produto.service';
import { ISubGrupoProduto } from 'app/entities/controle/sub-grupo-produto/sub-grupo-produto.model';
import { SubGrupoProdutoService } from 'app/entities/controle/sub-grupo-produto/service/sub-grupo-produto.service';

@Component({
  selector: 'jhi-estoque-update',
  templateUrl: './estoque-update.component.html',
})
export class EstoqueUpdateComponent implements OnInit {
  isSaving = false;

  produtosCollection: IProduto[] = [];
  grupoProdutosCollection: IGrupoProduto[] = [];
  subGrupoProdutosCollection: ISubGrupoProduto[] = [];

  editForm = this.fb.group({
    id: [],
    data: [null, [Validators.required]],
    qtde: [null, [Validators.required]],
    valorUnitario: [null, [Validators.required]],
    valorTotal: [null, [Validators.required]],
    produto: [],
    grupoProduto: [],
    subGrupoProduto: [],
  });

  constructor(
    protected estoqueService: EstoqueService,
    protected produtoService: ProdutoService,
    protected grupoProdutoService: GrupoProdutoService,
    protected subGrupoProdutoService: SubGrupoProdutoService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estoque }) => {
      this.updateForm(estoque);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const estoque = this.createFromForm();
    if (estoque.id !== undefined) {
      this.subscribeToSaveResponse(this.estoqueService.update(estoque));
    } else {
      this.subscribeToSaveResponse(this.estoqueService.create(estoque));
    }
  }

  trackProdutoById(_index: number, item: IProduto): number {
    return item.id!;
  }

  trackGrupoProdutoById(_index: number, item: IGrupoProduto): number {
    return item.id!;
  }

  trackSubGrupoProdutoById(_index: number, item: ISubGrupoProduto): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstoque>>): void {
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

  protected updateForm(estoque: IEstoque): void {
    this.editForm.patchValue({
      id: estoque.id,
      data: estoque.data,
      qtde: estoque.qtde,
      valorUnitario: estoque.valorUnitario,
      valorTotal: estoque.valorTotal,
      produto: estoque.produto,
      grupoProduto: estoque.grupoProduto,
      subGrupoProduto: estoque.subGrupoProduto,
    });

    this.produtosCollection = this.produtoService.addProdutoToCollectionIfMissing(this.produtosCollection, estoque.produto);
    this.grupoProdutosCollection = this.grupoProdutoService.addGrupoProdutoToCollectionIfMissing(
      this.grupoProdutosCollection,
      estoque.grupoProduto
    );
    this.subGrupoProdutosCollection = this.subGrupoProdutoService.addSubGrupoProdutoToCollectionIfMissing(
      this.subGrupoProdutosCollection,
      estoque.subGrupoProduto
    );
  }

  protected loadRelationshipsOptions(): void {
    this.produtoService
      .query({ filter: 'estoque-is-null' })
      .pipe(map((res: HttpResponse<IProduto[]>) => res.body ?? []))
      .pipe(
        map((produtos: IProduto[]) => this.produtoService.addProdutoToCollectionIfMissing(produtos, this.editForm.get('produto')!.value))
      )
      .subscribe((produtos: IProduto[]) => (this.produtosCollection = produtos));

    this.grupoProdutoService
      .query({ filter: 'estoque-is-null' })
      .pipe(map((res: HttpResponse<IGrupoProduto[]>) => res.body ?? []))
      .pipe(
        map((grupoProdutos: IGrupoProduto[]) =>
          this.grupoProdutoService.addGrupoProdutoToCollectionIfMissing(grupoProdutos, this.editForm.get('grupoProduto')!.value)
        )
      )
      .subscribe((grupoProdutos: IGrupoProduto[]) => (this.grupoProdutosCollection = grupoProdutos));

    this.subGrupoProdutoService
      .query({ filter: 'estoque-is-null' })
      .pipe(map((res: HttpResponse<ISubGrupoProduto[]>) => res.body ?? []))
      .pipe(
        map((subGrupoProdutos: ISubGrupoProduto[]) =>
          this.subGrupoProdutoService.addSubGrupoProdutoToCollectionIfMissing(subGrupoProdutos, this.editForm.get('subGrupoProduto')!.value)
        )
      )
      .subscribe((subGrupoProdutos: ISubGrupoProduto[]) => (this.subGrupoProdutosCollection = subGrupoProdutos));
  }

  protected createFromForm(): IEstoque {
    return {
      ...new Estoque(),
      id: this.editForm.get(['id'])!.value,
      data: this.editForm.get(['data'])!.value,
      qtde: this.editForm.get(['qtde'])!.value,
      valorUnitario: this.editForm.get(['valorUnitario'])!.value,
      valorTotal: this.editForm.get(['valorTotal'])!.value,
      produto: this.editForm.get(['produto'])!.value,
      grupoProduto: this.editForm.get(['grupoProduto'])!.value,
      subGrupoProduto: this.editForm.get(['subGrupoProduto'])!.value,
    };
  }
}
