import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEntradaEstoque, EntradaEstoque } from '../entrada-estoque.model';
import { EntradaEstoqueService } from '../service/entrada-estoque.service';
import { IProduto } from 'app/entities/controle/produto/produto.model';
import { ProdutoService } from 'app/entities/controle/produto/service/produto.service';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';
import { DadosPessoaisService } from 'app/entities/user/dados-pessoais/service/dados-pessoais.service';

@Component({
  selector: 'jhi-entrada-estoque-update',
  templateUrl: './entrada-estoque-update.component.html',
})
export class EntradaEstoqueUpdateComponent implements OnInit {
  isSaving = false;

  produtosCollection: IProduto[] = [];
  dadosPessoaisSharedCollection: IDadosPessoais[] = [];

  editForm = this.fb.group({
    id: [],
    data: [null, [Validators.required]],
    qtde: [null, [Validators.required]],
    valorUnitario: [null, [Validators.required]],
    obs: [null, [Validators.maxLength(50)]],
    produto: [],
    dadosPessoais: [],
  });

  constructor(
    protected entradaEstoqueService: EntradaEstoqueService,
    protected produtoService: ProdutoService,
    protected dadosPessoaisService: DadosPessoaisService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entradaEstoque }) => {
      this.updateForm(entradaEstoque);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const entradaEstoque = this.createFromForm();
    if (entradaEstoque.id !== undefined) {
      this.subscribeToSaveResponse(this.entradaEstoqueService.update(entradaEstoque));
    } else {
      this.subscribeToSaveResponse(this.entradaEstoqueService.create(entradaEstoque));
    }
  }

  trackProdutoById(_index: number, item: IProduto): number {
    return item.id!;
  }

  trackDadosPessoaisById(_index: number, item: IDadosPessoais): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEntradaEstoque>>): void {
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

  protected updateForm(entradaEstoque: IEntradaEstoque): void {
    this.editForm.patchValue({
      id: entradaEstoque.id,
      data: entradaEstoque.data,
      qtde: entradaEstoque.qtde,
      valorUnitario: entradaEstoque.valorUnitario,
      obs: entradaEstoque.obs,
      produto: entradaEstoque.produto,
      dadosPessoais: entradaEstoque.dadosPessoais,
    });

    this.produtosCollection = this.produtoService.addProdutoToCollectionIfMissing(this.produtosCollection, entradaEstoque.produto);
    this.dadosPessoaisSharedCollection = this.dadosPessoaisService.addDadosPessoaisToCollectionIfMissing(
      this.dadosPessoaisSharedCollection,
      entradaEstoque.dadosPessoais
    );
  }

  protected loadRelationshipsOptions(): void {
    this.produtoService
      .query({ filter: 'entradaestoque-is-null' })
      .pipe(map((res: HttpResponse<IProduto[]>) => res.body ?? []))
      .pipe(
        map((produtos: IProduto[]) => this.produtoService.addProdutoToCollectionIfMissing(produtos, this.editForm.get('produto')!.value))
      )
      .subscribe((produtos: IProduto[]) => (this.produtosCollection = produtos));

    this.dadosPessoaisService
      .query()
      .pipe(map((res: HttpResponse<IDadosPessoais[]>) => res.body ?? []))
      .pipe(
        map((dadosPessoais: IDadosPessoais[]) =>
          this.dadosPessoaisService.addDadosPessoaisToCollectionIfMissing(dadosPessoais, this.editForm.get('dadosPessoais')!.value)
        )
      )
      .subscribe((dadosPessoais: IDadosPessoais[]) => (this.dadosPessoaisSharedCollection = dadosPessoais));
  }

  protected createFromForm(): IEntradaEstoque {
    return {
      ...new EntradaEstoque(),
      id: this.editForm.get(['id'])!.value,
      data: this.editForm.get(['data'])!.value,
      qtde: this.editForm.get(['qtde'])!.value,
      valorUnitario: this.editForm.get(['valorUnitario'])!.value,
      obs: this.editForm.get(['obs'])!.value,
      produto: this.editForm.get(['produto'])!.value,
      dadosPessoais: this.editForm.get(['dadosPessoais'])!.value,
    };
  }
}
