import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IFotoSaidaEstoque, FotoSaidaEstoque } from '../foto-saida-estoque.model';
import { FotoSaidaEstoqueService } from '../service/foto-saida-estoque.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ISaidaEstoque } from 'app/entities/controle/saida-estoque/saida-estoque.model';
import { SaidaEstoqueService } from 'app/entities/controle/saida-estoque/service/saida-estoque.service';

@Component({
  selector: 'jhi-foto-saida-estoque-update',
  templateUrl: './foto-saida-estoque-update.component.html',
})
export class FotoSaidaEstoqueUpdateComponent implements OnInit {
  isSaving = false;

  saidaEstoquesSharedCollection: ISaidaEstoque[] = [];

  editForm = this.fb.group({
    id: [],
    conteudo: [null, [Validators.required]],
    conteudoContentType: [],
    saidaEstoque: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected fotoSaidaEstoqueService: FotoSaidaEstoqueService,
    protected saidaEstoqueService: SaidaEstoqueService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fotoSaidaEstoque }) => {
      this.updateForm(fotoSaidaEstoque);

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
    const fotoSaidaEstoque = this.createFromForm();
    if (fotoSaidaEstoque.id !== undefined) {
      this.subscribeToSaveResponse(this.fotoSaidaEstoqueService.update(fotoSaidaEstoque));
    } else {
      this.subscribeToSaveResponse(this.fotoSaidaEstoqueService.create(fotoSaidaEstoque));
    }
  }

  trackSaidaEstoqueById(_index: number, item: ISaidaEstoque): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFotoSaidaEstoque>>): void {
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

  protected updateForm(fotoSaidaEstoque: IFotoSaidaEstoque): void {
    this.editForm.patchValue({
      id: fotoSaidaEstoque.id,
      conteudo: fotoSaidaEstoque.conteudo,
      conteudoContentType: fotoSaidaEstoque.conteudoContentType,
      saidaEstoque: fotoSaidaEstoque.saidaEstoque,
    });

    this.saidaEstoquesSharedCollection = this.saidaEstoqueService.addSaidaEstoqueToCollectionIfMissing(
      this.saidaEstoquesSharedCollection,
      fotoSaidaEstoque.saidaEstoque
    );
  }

  protected loadRelationshipsOptions(): void {
    this.saidaEstoqueService
      .query()
      .pipe(map((res: HttpResponse<ISaidaEstoque[]>) => res.body ?? []))
      .pipe(
        map((saidaEstoques: ISaidaEstoque[]) =>
          this.saidaEstoqueService.addSaidaEstoqueToCollectionIfMissing(saidaEstoques, this.editForm.get('saidaEstoque')!.value)
        )
      )
      .subscribe((saidaEstoques: ISaidaEstoque[]) => (this.saidaEstoquesSharedCollection = saidaEstoques));
  }

  protected createFromForm(): IFotoSaidaEstoque {
    return {
      ...new FotoSaidaEstoque(),
      id: this.editForm.get(['id'])!.value,
      conteudoContentType: this.editForm.get(['conteudoContentType'])!.value,
      conteudo: this.editForm.get(['conteudo'])!.value,
      saidaEstoque: this.editForm.get(['saidaEstoque'])!.value,
    };
  }
}
