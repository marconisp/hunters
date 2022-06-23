import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IFotoDocumento, FotoDocumento } from '../foto-documento.model';
import { FotoDocumentoService } from '../service/foto-documento.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IDocumento } from 'app/entities/user/documento/documento.model';
import { DocumentoService } from 'app/entities/user/documento/service/documento.service';

@Component({
  selector: 'jhi-foto-documento-update',
  templateUrl: './foto-documento-update.component.html',
})
export class FotoDocumentoUpdateComponent implements OnInit {
  isSaving = false;

  documentosSharedCollection: IDocumento[] = [];

  editForm = this.fb.group({
    id: [],
    conteudo: [null, [Validators.required]],
    conteudoContentType: [],
    documento: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected fotoDocumentoService: FotoDocumentoService,
    protected documentoService: DocumentoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fotoDocumento }) => {
      this.updateForm(fotoDocumento);

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
    const fotoDocumento = this.createFromForm();
    if (fotoDocumento.id !== undefined) {
      this.subscribeToSaveResponse(this.fotoDocumentoService.update(fotoDocumento));
    } else {
      this.subscribeToSaveResponse(this.fotoDocumentoService.create(fotoDocumento));
    }
  }

  trackDocumentoById(_index: number, item: IDocumento): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFotoDocumento>>): void {
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

  protected updateForm(fotoDocumento: IFotoDocumento): void {
    this.editForm.patchValue({
      id: fotoDocumento.id,
      conteudo: fotoDocumento.conteudo,
      conteudoContentType: fotoDocumento.conteudoContentType,
      documento: fotoDocumento.documento,
    });

    this.documentosSharedCollection = this.documentoService.addDocumentoToCollectionIfMissing(
      this.documentosSharedCollection,
      fotoDocumento.documento
    );
  }

  protected loadRelationshipsOptions(): void {
    this.documentoService
      .query()
      .pipe(map((res: HttpResponse<IDocumento[]>) => res.body ?? []))
      .pipe(
        map((documentos: IDocumento[]) =>
          this.documentoService.addDocumentoToCollectionIfMissing(documentos, this.editForm.get('documento')!.value)
        )
      )
      .subscribe((documentos: IDocumento[]) => (this.documentosSharedCollection = documentos));
  }

  protected createFromForm(): IFotoDocumento {
    return {
      ...new FotoDocumento(),
      id: this.editForm.get(['id'])!.value,
      conteudoContentType: this.editForm.get(['conteudoContentType'])!.value,
      conteudo: this.editForm.get(['conteudo'])!.value,
      documento: this.editForm.get(['documento'])!.value,
    };
  }
}
