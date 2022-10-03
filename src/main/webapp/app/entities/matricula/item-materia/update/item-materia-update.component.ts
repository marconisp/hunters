import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IItemMateria, ItemMateria } from '../item-materia.model';
import { ItemMateriaService } from '../service/item-materia.service';
import { IMateria } from 'app/entities/matricula/materia/materia.model';
import { MateriaService } from 'app/entities/matricula/materia/service/materia.service';
import { IAcompanhamentoAluno } from 'app/entities/matricula/acompanhamento-aluno/acompanhamento-aluno.model';
import { AcompanhamentoAlunoService } from 'app/entities/matricula/acompanhamento-aluno/service/acompanhamento-aluno.service';

@Component({
  selector: 'jhi-item-materia-update',
  templateUrl: './item-materia-update.component.html',
})
export class ItemMateriaUpdateComponent implements OnInit {
  isSaving = false;

  materiasCollection: IMateria[] = [];
  acompanhamentoAlunosSharedCollection: IAcompanhamentoAluno[] = [];

  editForm = this.fb.group({
    id: [],
    nota: [null, [Validators.required, Validators.maxLength(20)]],
    obs: [null, [Validators.maxLength(50)]],
    materia: [],
    acompanhamentoAluno: [],
  });

  constructor(
    protected itemMateriaService: ItemMateriaService,
    protected materiaService: MateriaService,
    protected acompanhamentoAlunoService: AcompanhamentoAlunoService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ itemMateria }) => {
      this.updateForm(itemMateria);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const itemMateria = this.createFromForm();
    if (itemMateria.id !== undefined) {
      this.subscribeToSaveResponse(this.itemMateriaService.update(itemMateria));
    } else {
      this.subscribeToSaveResponse(this.itemMateriaService.create(itemMateria));
    }
  }

  trackMateriaById(_index: number, item: IMateria): number {
    return item.id!;
  }

  trackAcompanhamentoAlunoById(_index: number, item: IAcompanhamentoAluno): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItemMateria>>): void {
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

  protected updateForm(itemMateria: IItemMateria): void {
    this.editForm.patchValue({
      id: itemMateria.id,
      nota: itemMateria.nota,
      obs: itemMateria.obs,
      materia: itemMateria.materia,
      acompanhamentoAluno: itemMateria.acompanhamentoAluno,
    });

    this.materiasCollection = this.materiaService.addMateriaToCollectionIfMissing(this.materiasCollection, itemMateria.materia);
    this.acompanhamentoAlunosSharedCollection = this.acompanhamentoAlunoService.addAcompanhamentoAlunoToCollectionIfMissing(
      this.acompanhamentoAlunosSharedCollection,
      itemMateria.acompanhamentoAluno
    );
  }

  protected loadRelationshipsOptions(): void {
    this.materiaService
      .query({ filter: 'itemmateria-is-null' })
      .pipe(map((res: HttpResponse<IMateria[]>) => res.body ?? []))
      .pipe(
        map((materias: IMateria[]) => this.materiaService.addMateriaToCollectionIfMissing(materias, this.editForm.get('materia')!.value))
      )
      .subscribe((materias: IMateria[]) => (this.materiasCollection = materias));

    this.acompanhamentoAlunoService
      .query()
      .pipe(map((res: HttpResponse<IAcompanhamentoAluno[]>) => res.body ?? []))
      .pipe(
        map((acompanhamentoAlunos: IAcompanhamentoAluno[]) =>
          this.acompanhamentoAlunoService.addAcompanhamentoAlunoToCollectionIfMissing(
            acompanhamentoAlunos,
            this.editForm.get('acompanhamentoAluno')!.value
          )
        )
      )
      .subscribe((acompanhamentoAlunos: IAcompanhamentoAluno[]) => (this.acompanhamentoAlunosSharedCollection = acompanhamentoAlunos));
  }

  protected createFromForm(): IItemMateria {
    return {
      ...new ItemMateria(),
      id: this.editForm.get(['id'])!.value,
      nota: this.editForm.get(['nota'])!.value,
      obs: this.editForm.get(['obs'])!.value,
      materia: this.editForm.get(['materia'])!.value,
      acompanhamentoAluno: this.editForm.get(['acompanhamentoAluno'])!.value,
    };
  }
}
