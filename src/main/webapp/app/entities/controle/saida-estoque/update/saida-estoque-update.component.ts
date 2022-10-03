import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISaidaEstoque, SaidaEstoque } from '../saida-estoque.model';
import { SaidaEstoqueService } from '../service/saida-estoque.service';
import { IProduto } from 'app/entities/controle/produto/produto.model';
import { ProdutoService } from 'app/entities/controle/produto/service/produto.service';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';
import { DadosPessoaisService } from 'app/entities/user/dados-pessoais/service/dados-pessoais.service';

@Component({
  selector: 'jhi-saida-estoque-update',
  templateUrl: './saida-estoque-update.component.html',
})
export class SaidaEstoqueUpdateComponent implements OnInit {
  isSaving = false;

  produtosCollection: IProduto[] = [];
  dadosPessoaisSharedCollection: IDadosPessoais[] = [];

  editForm = this.fb.group({
    id: [],
    data: [null, [Validators.required]],
    qtde: [null, [Validators.required]],
    valorUnitario: [null, [Validators.required]],
    obs: [null, [Validators.maxLength(100)]],
    produto: [],
    dadosPessoais: [],
  });

  constructor(
    protected saidaEstoqueService: SaidaEstoqueService,
    protected produtoService: ProdutoService,
    protected dadosPessoaisService: DadosPessoaisService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ saidaEstoque }) => {
      this.updateForm(saidaEstoque);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const saidaEstoque = this.createFromForm();
    if (saidaEstoque.id !== undefined) {
      this.subscribeToSaveResponse(this.saidaEstoqueService.update(saidaEstoque));
    } else {
      this.subscribeToSaveResponse(this.saidaEstoqueService.create(saidaEstoque));
    }
  }

  trackProdutoById(_index: number, item: IProduto): number {
    return item.id!;
  }

  trackDadosPessoaisById(_index: number, item: IDadosPessoais): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISaidaEstoque>>): void {
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

  protected updateForm(saidaEstoque: ISaidaEstoque): void {
    this.editForm.patchValue({
      id: saidaEstoque.id,
      data: saidaEstoque.data,
      qtde: saidaEstoque.qtde,
      valorUnitario: saidaEstoque.valorUnitario,
      obs: saidaEstoque.obs,
      produto: saidaEstoque.produto,
      dadosPessoais: saidaEstoque.dadosPessoais,
    });

    this.produtosCollection = this.produtoService.addProdutoToCollectionIfMissing(this.produtosCollection, saidaEstoque.produto);
    this.dadosPessoaisSharedCollection = this.dadosPessoaisService.addDadosPessoaisToCollectionIfMissing(
      this.dadosPessoaisSharedCollection,
      saidaEstoque.dadosPessoais
    );
  }

  protected loadRelationshipsOptions(): void {
    this.produtoService
      .query({ filter: 'saidaestoque-is-null' })
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

  protected createFromForm(): ISaidaEstoque {
    return {
      ...new SaidaEstoque(),
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
