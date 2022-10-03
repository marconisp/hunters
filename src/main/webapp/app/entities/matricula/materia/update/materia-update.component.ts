import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IMateria, Materia } from '../materia.model';
import { MateriaService } from '../service/materia.service';

@Component({
  selector: 'jhi-materia-update',
  templateUrl: './materia-update.component.html',
})
export class MateriaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(50)]],
  });

  constructor(protected materiaService: MateriaService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ materia }) => {
      this.updateForm(materia);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const materia = this.createFromForm();
    if (materia.id !== undefined) {
      this.subscribeToSaveResponse(this.materiaService.update(materia));
    } else {
      this.subscribeToSaveResponse(this.materiaService.create(materia));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMateria>>): void {
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

  protected updateForm(materia: IMateria): void {
    this.editForm.patchValue({
      id: materia.id,
      nome: materia.nome,
    });
  }

  protected createFromForm(): IMateria {
    return {
      ...new Materia(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
    };
  }
}
