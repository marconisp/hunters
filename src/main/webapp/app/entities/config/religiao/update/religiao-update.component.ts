import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IReligiao, Religiao } from '../religiao.model';
import { ReligiaoService } from '../service/religiao.service';

@Component({
  selector: 'jhi-religiao-update',
  templateUrl: './religiao-update.component.html',
})
export class ReligiaoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    codigo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(20)]],
    descricao: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
  });

  constructor(protected religiaoService: ReligiaoService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ religiao }) => {
      this.updateForm(religiao);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const religiao = this.createFromForm();
    if (religiao.id !== undefined) {
      this.subscribeToSaveResponse(this.religiaoService.update(religiao));
    } else {
      this.subscribeToSaveResponse(this.religiaoService.create(religiao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReligiao>>): void {
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

  protected updateForm(religiao: IReligiao): void {
    this.editForm.patchValue({
      id: religiao.id,
      codigo: religiao.codigo,
      descricao: religiao.descricao,
    });
  }

  protected createFromForm(): IReligiao {
    return {
      ...new Religiao(),
      id: this.editForm.get(['id'])!.value,
      codigo: this.editForm.get(['codigo'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
    };
  }
}
