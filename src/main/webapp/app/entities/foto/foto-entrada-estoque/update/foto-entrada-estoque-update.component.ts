import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IFotoEntradaEstoque, FotoEntradaEstoque } from '../foto-entrada-estoque.model';
import { FotoEntradaEstoqueService } from '../service/foto-entrada-estoque.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IEntradaEstoque } from 'app/entities/controle/entrada-estoque/entrada-estoque.model';
import { EntradaEstoqueService } from 'app/entities/controle/entrada-estoque/service/entrada-estoque.service';

@Component({
  selector: 'jhi-foto-entrada-estoque-update',
  templateUrl: './foto-entrada-estoque-update.component.html',
})
export class FotoEntradaEstoqueUpdateComponent implements OnInit {
  isSaving = false;

  entradaEstoquesSharedCollection: IEntradaEstoque[] = [];

  editForm = this.fb.group({
    id: [],
    conteudo: [null, [Validators.required]],
    conteudoContentType: [],
    entradaEstoque: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected fotoEntradaEstoqueService: FotoEntradaEstoqueService,
    protected entradaEstoqueService: EntradaEstoqueService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fotoEntradaEstoque }) => {
      this.updateForm(fotoEntradaEstoque);

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
    const fotoEntradaEstoque = this.createFromForm();
    if (fotoEntradaEstoque.id !== undefined) {
      this.subscribeToSaveResponse(this.fotoEntradaEstoqueService.update(fotoEntradaEstoque));
    } else {
      this.subscribeToSaveResponse(this.fotoEntradaEstoqueService.create(fotoEntradaEstoque));
    }
  }

  trackEntradaEstoqueById(_index: number, item: IEntradaEstoque): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFotoEntradaEstoque>>): void {
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

  protected updateForm(fotoEntradaEstoque: IFotoEntradaEstoque): void {
    this.editForm.patchValue({
      id: fotoEntradaEstoque.id,
      conteudo: fotoEntradaEstoque.conteudo,
      conteudoContentType: fotoEntradaEstoque.conteudoContentType,
      entradaEstoque: fotoEntradaEstoque.entradaEstoque,
    });

    this.entradaEstoquesSharedCollection = this.entradaEstoqueService.addEntradaEstoqueToCollectionIfMissing(
      this.entradaEstoquesSharedCollection,
      fotoEntradaEstoque.entradaEstoque
    );
  }

  protected loadRelationshipsOptions(): void {
    this.entradaEstoqueService
      .query()
      .pipe(map((res: HttpResponse<IEntradaEstoque[]>) => res.body ?? []))
      .pipe(
        map((entradaEstoques: IEntradaEstoque[]) =>
          this.entradaEstoqueService.addEntradaEstoqueToCollectionIfMissing(entradaEstoques, this.editForm.get('entradaEstoque')!.value)
        )
      )
      .subscribe((entradaEstoques: IEntradaEstoque[]) => (this.entradaEstoquesSharedCollection = entradaEstoques));
  }

  protected createFromForm(): IFotoEntradaEstoque {
    return {
      ...new FotoEntradaEstoque(),
      id: this.editForm.get(['id'])!.value,
      conteudoContentType: this.editForm.get(['conteudoContentType'])!.value,
      conteudo: this.editForm.get(['conteudo'])!.value,
      entradaEstoque: this.editForm.get(['entradaEstoque'])!.value,
    };
  }
}
