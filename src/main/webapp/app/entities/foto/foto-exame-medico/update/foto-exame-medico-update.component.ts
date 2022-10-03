import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IFotoExameMedico, FotoExameMedico } from '../foto-exame-medico.model';
import { FotoExameMedicoService } from '../service/foto-exame-medico.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IExameMedico } from 'app/entities/evento/exame-medico/exame-medico.model';
import { ExameMedicoService } from 'app/entities/evento/exame-medico/service/exame-medico.service';

@Component({
  selector: 'jhi-foto-exame-medico-update',
  templateUrl: './foto-exame-medico-update.component.html',
})
export class FotoExameMedicoUpdateComponent implements OnInit {
  isSaving = false;

  exameMedicosSharedCollection: IExameMedico[] = [];

  editForm = this.fb.group({
    id: [],
    foto: [null, [Validators.required]],
    fotoContentType: [],
    exameMedico: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected fotoExameMedicoService: FotoExameMedicoService,
    protected exameMedicoService: ExameMedicoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fotoExameMedico }) => {
      this.updateForm(fotoExameMedico);

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
    const fotoExameMedico = this.createFromForm();
    if (fotoExameMedico.id !== undefined) {
      this.subscribeToSaveResponse(this.fotoExameMedicoService.update(fotoExameMedico));
    } else {
      this.subscribeToSaveResponse(this.fotoExameMedicoService.create(fotoExameMedico));
    }
  }

  trackExameMedicoById(_index: number, item: IExameMedico): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFotoExameMedico>>): void {
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

  protected updateForm(fotoExameMedico: IFotoExameMedico): void {
    this.editForm.patchValue({
      id: fotoExameMedico.id,
      foto: fotoExameMedico.foto,
      fotoContentType: fotoExameMedico.fotoContentType,
      exameMedico: fotoExameMedico.exameMedico,
    });

    this.exameMedicosSharedCollection = this.exameMedicoService.addExameMedicoToCollectionIfMissing(
      this.exameMedicosSharedCollection,
      fotoExameMedico.exameMedico
    );
  }

  protected loadRelationshipsOptions(): void {
    this.exameMedicoService
      .query()
      .pipe(map((res: HttpResponse<IExameMedico[]>) => res.body ?? []))
      .pipe(
        map((exameMedicos: IExameMedico[]) =>
          this.exameMedicoService.addExameMedicoToCollectionIfMissing(exameMedicos, this.editForm.get('exameMedico')!.value)
        )
      )
      .subscribe((exameMedicos: IExameMedico[]) => (this.exameMedicosSharedCollection = exameMedicos));
  }

  protected createFromForm(): IFotoExameMedico {
    return {
      ...new FotoExameMedico(),
      id: this.editForm.get(['id'])!.value,
      fotoContentType: this.editForm.get(['fotoContentType'])!.value,
      foto: this.editForm.get(['foto'])!.value,
      exameMedico: this.editForm.get(['exameMedico'])!.value,
    };
  }
}
