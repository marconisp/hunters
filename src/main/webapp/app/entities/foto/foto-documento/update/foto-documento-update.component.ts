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
import { IDocumento, Documento } from 'app/entities/user/documento/documento.model';

@Component({
  selector: 'jhi-foto-documento-update',
  templateUrl: './foto-documento-update.component.html',
})
export class FotoDocumentoUpdateComponent implements OnInit {
  isSaving = false;

  documento?: IDocumento;

  editForm = this.fb.group({
    id: [],
    conteudo: [null, [Validators.required]],
    conteudoContentType: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected fotoDocumentoService: FotoDocumentoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fotoDocumento, documento }) => {
      this.updateForm(fotoDocumento);
      this.documento = documento ?? new Documento();
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

    fotoDocumento.documento = this.documento;
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
    });
  }

  protected createFromForm(): IFotoDocumento {
    return {
      ...new FotoDocumento(),
      id: this.editForm.get(['id'])!.value,
      conteudoContentType: this.editForm.get(['conteudoContentType'])!.value,
      conteudo: this.editForm.get(['conteudo'])!.value,
    };
  }
}
