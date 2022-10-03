import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IFotoProduto, FotoProduto } from '../foto-produto.model';
import { FotoProdutoService } from '../service/foto-produto.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IProduto } from 'app/entities/controle/produto/produto.model';
import { ProdutoService } from 'app/entities/controle/produto/service/produto.service';

@Component({
  selector: 'jhi-foto-produto-update',
  templateUrl: './foto-produto-update.component.html',
})
export class FotoProdutoUpdateComponent implements OnInit {
  isSaving = false;

  produtosSharedCollection: IProduto[] = [];

  editForm = this.fb.group({
    id: [],
    conteudo: [null, [Validators.required]],
    conteudoContentType: [],
    produto: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected fotoProdutoService: FotoProdutoService,
    protected produtoService: ProdutoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fotoProduto }) => {
      this.updateForm(fotoProduto);

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('hunterappApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fotoProduto = this.createFromForm();
    if (fotoProduto.id !== undefined) {
      this.subscribeToSaveResponse(this.fotoProdutoService.update(fotoProduto));
    } else {
      this.subscribeToSaveResponse(this.fotoProdutoService.create(fotoProduto));
    }
  }

  trackProdutoById(_index: number, item: IProduto): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFotoProduto>>): void {
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

  protected updateForm(fotoProduto: IFotoProduto): void {
    this.editForm.patchValue({
      id: fotoProduto.id,
      conteudo: fotoProduto.conteudo,
      conteudoContentType: fotoProduto.conteudoContentType,
      produto: fotoProduto.produto,
    });

    this.produtosSharedCollection = this.produtoService.addProdutoToCollectionIfMissing(this.produtosSharedCollection, fotoProduto.produto);
  }

  protected loadRelationshipsOptions(): void {
    this.produtoService
      .query()
      .pipe(map((res: HttpResponse<IProduto[]>) => res.body ?? []))
      .pipe(
        map((produtos: IProduto[]) => this.produtoService.addProdutoToCollectionIfMissing(produtos, this.editForm.get('produto')!.value))
      )
      .subscribe((produtos: IProduto[]) => (this.produtosSharedCollection = produtos));
  }

  protected createFromForm(): IFotoProduto {
    return {
      ...new FotoProduto(),
      id: this.editForm.get(['id'])!.value,
      conteudoContentType: this.editForm.get(['conteudoContentType'])!.value,
      conteudo: this.editForm.get(['conteudo'])!.value,
      produto: this.editForm.get(['produto'])!.value,
    };
  }
}
