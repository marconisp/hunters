import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IFotoPagar, FotoPagar } from '../foto-pagar.model';
import { FotoPagarService } from '../service/foto-pagar.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IPagar, Pagar } from 'app/entities/controle/pagar/pagar.model';
import { PagarService } from 'app/entities/controle/pagar/service/pagar.service';

@Component({
  selector: 'jhi-foto-pagar-update',
  templateUrl: './foto-pagar-update.component.html',
})
export class FotoPagarUpdateComponent implements OnInit {
  isSaving = false;

  pagarsSharedCollection: IPagar[] = [];
  pagar?: IPagar;

  editForm = this.fb.group({
    id: [],
    conteudo: [null, [Validators.required]],
    conteudoContentType: [],
    pagar: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected fotoPagarService: FotoPagarService,
    protected pagarService: PagarService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fotoPagar, pagar }) => {
      this.updateForm(fotoPagar);
      this.pagar = pagar ?? new Pagar();
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
    const fotoPagar = this.createFromForm();
    fotoPagar.pagar = this.pagar;
    if (fotoPagar.id !== undefined) {
      this.subscribeToSaveResponse(this.fotoPagarService.update(fotoPagar));
    } else {
      this.subscribeToSaveResponse(this.fotoPagarService.create(fotoPagar));
    }
  }

  trackPagarById(_index: number, item: IPagar): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFotoPagar>>): void {
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

  protected updateForm(fotoPagar: IFotoPagar): void {
    this.editForm.patchValue({
      id: fotoPagar.id,
      conteudo: fotoPagar.conteudo,
      conteudoContentType: fotoPagar.conteudoContentType,
      pagar: fotoPagar.pagar,
    });

    this.pagarsSharedCollection = this.pagarService.addPagarToCollectionIfMissing(this.pagarsSharedCollection, fotoPagar.pagar);
  }

  protected loadRelationshipsOptions(): void {
    this.pagarService
      .query()
      .pipe(map((res: HttpResponse<IPagar[]>) => res.body ?? []))
      .pipe(map((pagars: IPagar[]) => this.pagarService.addPagarToCollectionIfMissing(pagars, this.editForm.get('pagar')!.value)))
      .subscribe((pagars: IPagar[]) => (this.pagarsSharedCollection = pagars));
  }

  protected createFromForm(): IFotoPagar {
    return {
      ...new FotoPagar(),
      id: this.editForm.get(['id'])!.value,
      conteudoContentType: this.editForm.get(['conteudoContentType'])!.value,
      conteudo: this.editForm.get(['conteudo'])!.value,
      pagar: this.editForm.get(['pagar'])!.value,
    };
  }
}
