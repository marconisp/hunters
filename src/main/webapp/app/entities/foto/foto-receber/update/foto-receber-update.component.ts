import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IFotoReceber, FotoReceber } from '../foto-receber.model';
import { FotoReceberService } from '../service/foto-receber.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IReceber, Receber } from 'app/entities/controle/receber/receber.model';
import { ReceberService } from 'app/entities/controle/receber/service/receber.service';

@Component({
  selector: 'jhi-foto-receber-update',
  templateUrl: './foto-receber-update.component.html',
})
export class FotoReceberUpdateComponent implements OnInit {
  isSaving = false;

  recebersSharedCollection: IReceber[] = [];
  receber?: IReceber;

  editForm = this.fb.group({
    id: [],
    conteudo: [null, [Validators.required]],
    conteudoContentType: [],
    receber: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected fotoReceberService: FotoReceberService,
    protected receberService: ReceberService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fotoReceber, receber }) => {
      this.updateForm(fotoReceber);
      this.receber = receber ?? new Receber();
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
    const fotoReceber = this.createFromForm();
    fotoReceber.receber = this.receber;
    if (fotoReceber.id !== undefined) {
      this.subscribeToSaveResponse(this.fotoReceberService.update(fotoReceber));
    } else {
      this.subscribeToSaveResponse(this.fotoReceberService.create(fotoReceber));
    }
  }

  trackReceberById(_index: number, item: IReceber): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFotoReceber>>): void {
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

  protected updateForm(fotoReceber: IFotoReceber): void {
    this.editForm.patchValue({
      id: fotoReceber.id,
      conteudo: fotoReceber.conteudo,
      conteudoContentType: fotoReceber.conteudoContentType,
      receber: fotoReceber.receber,
    });

    this.recebersSharedCollection = this.receberService.addReceberToCollectionIfMissing(this.recebersSharedCollection, fotoReceber.receber);
  }

  protected loadRelationshipsOptions(): void {
    this.receberService
      .query()
      .pipe(map((res: HttpResponse<IReceber[]>) => res.body ?? []))
      .pipe(
        map((recebers: IReceber[]) => this.receberService.addReceberToCollectionIfMissing(recebers, this.editForm.get('receber')!.value))
      )
      .subscribe((recebers: IReceber[]) => (this.recebersSharedCollection = recebers));
  }

  protected createFromForm(): IFotoReceber {
    return {
      ...new FotoReceber(),
      id: this.editForm.get(['id'])!.value,
      conteudoContentType: this.editForm.get(['conteudoContentType'])!.value,
      conteudo: this.editForm.get(['conteudo'])!.value,
      receber: this.editForm.get(['receber'])!.value,
    };
  }
}
