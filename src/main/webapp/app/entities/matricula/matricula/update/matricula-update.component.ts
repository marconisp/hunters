import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IMatricula, Matricula } from '../matricula.model';
import { MatriculaService } from '../service/matricula.service';
import { ITurma } from 'app/entities/matricula/turma/turma.model';
import { TurmaService } from 'app/entities/matricula/turma/service/turma.service';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';
import { DadosPessoaisService } from 'app/entities/user/dados-pessoais/service/dados-pessoais.service';

@Component({
  selector: 'jhi-matricula-update',
  templateUrl: './matricula-update.component.html',
})
export class MatriculaUpdateComponent implements OnInit {
  isSaving = false;

  turmasCollection: ITurma[] = [];
  dadosPessoaisSharedCollection: IDadosPessoais[] = [];

  editForm = this.fb.group({
    id: [],
    data: [null, [Validators.required]],
    obs: [null, [Validators.maxLength(100)]],
    turma: [],
    dadosPessoais: [],
  });

  constructor(
    protected matriculaService: MatriculaService,
    protected turmaService: TurmaService,
    protected dadosPessoaisService: DadosPessoaisService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matricula }) => {
      this.updateForm(matricula);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const matricula = this.createFromForm();
    if (matricula.id !== undefined) {
      this.subscribeToSaveResponse(this.matriculaService.update(matricula));
    } else {
      this.subscribeToSaveResponse(this.matriculaService.create(matricula));
    }
  }

  trackTurmaById(_index: number, item: ITurma): number {
    return item.id!;
  }

  trackDadosPessoaisById(_index: number, item: IDadosPessoais): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMatricula>>): void {
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

  protected updateForm(matricula: IMatricula): void {
    this.editForm.patchValue({
      id: matricula.id,
      data: matricula.data,
      obs: matricula.obs,
      turma: matricula.turma,
      dadosPessoais: matricula.dadosPessoais,
    });

    this.turmasCollection = this.turmaService.addTurmaToCollectionIfMissing(this.turmasCollection, matricula.turma);
    this.dadosPessoaisSharedCollection = this.dadosPessoaisService.addDadosPessoaisToCollectionIfMissing(
      this.dadosPessoaisSharedCollection,
      matricula.dadosPessoais
    );
  }

  protected loadRelationshipsOptions(): void {
    this.turmaService
      .query({ filter: 'matricula-is-null' })
      .pipe(map((res: HttpResponse<ITurma[]>) => res.body ?? []))
      .pipe(map((turmas: ITurma[]) => this.turmaService.addTurmaToCollectionIfMissing(turmas, this.editForm.get('turma')!.value)))
      .subscribe((turmas: ITurma[]) => (this.turmasCollection = turmas));

    this.dadosPessoaisService
      .query()
      .pipe(map((res: HttpResponse<IDadosPessoais[]>) => res.body ?? []))
      .pipe(
        map((dadosPessoais: IDadosPessoais[]) =>
          this.dadosPessoaisService.addDadosPessoaisToCollectionIfMissing(dadosPessoais, this.editForm.get('dadosPessoais')!.value)
        )
      )
      .subscribe((dadosPessoais: IDadosPessoais[]) => (this.dadosPessoaisSharedCollection = dadosPessoais));
  }

  protected createFromForm(): IMatricula {
    return {
      ...new Matricula(),
      id: this.editForm.get(['id'])!.value,
      data: this.editForm.get(['data'])!.value,
      obs: this.editForm.get(['obs'])!.value,
      turma: this.editForm.get(['turma'])!.value,
      dadosPessoais: this.editForm.get(['dadosPessoais'])!.value,
    };
  }
}
