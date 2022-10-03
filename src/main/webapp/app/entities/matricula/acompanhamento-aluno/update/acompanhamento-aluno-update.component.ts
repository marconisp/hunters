import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAcompanhamentoAluno, AcompanhamentoAluno } from '../acompanhamento-aluno.model';
import { AcompanhamentoAlunoService } from '../service/acompanhamento-aluno.service';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';
import { DadosPessoaisService } from 'app/entities/user/dados-pessoais/service/dados-pessoais.service';
import { Ensino } from 'app/entities/enumerations/ensino.model';

@Component({
  selector: 'jhi-acompanhamento-aluno-update',
  templateUrl: './acompanhamento-aluno-update.component.html',
})
export class AcompanhamentoAlunoUpdateComponent implements OnInit {
  isSaving = false;
  ensinoValues = Object.keys(Ensino);

  dadosPessoaisSharedCollection: IDadosPessoais[] = [];

  editForm = this.fb.group({
    id: [],
    ano: [null, [Validators.required]],
    ensino: [null, [Validators.required]],
    bimestre: [null, [Validators.required]],
    dadosPessoais: [],
  });

  constructor(
    protected acompanhamentoAlunoService: AcompanhamentoAlunoService,
    protected dadosPessoaisService: DadosPessoaisService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ acompanhamentoAluno }) => {
      this.updateForm(acompanhamentoAluno);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const acompanhamentoAluno = this.createFromForm();
    if (acompanhamentoAluno.id !== undefined) {
      this.subscribeToSaveResponse(this.acompanhamentoAlunoService.update(acompanhamentoAluno));
    } else {
      this.subscribeToSaveResponse(this.acompanhamentoAlunoService.create(acompanhamentoAluno));
    }
  }

  trackDadosPessoaisById(_index: number, item: IDadosPessoais): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAcompanhamentoAluno>>): void {
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

  protected updateForm(acompanhamentoAluno: IAcompanhamentoAluno): void {
    this.editForm.patchValue({
      id: acompanhamentoAluno.id,
      ano: acompanhamentoAluno.ano,
      ensino: acompanhamentoAluno.ensino,
      bimestre: acompanhamentoAluno.bimestre,
      dadosPessoais: acompanhamentoAluno.dadosPessoais,
    });

    this.dadosPessoaisSharedCollection = this.dadosPessoaisService.addDadosPessoaisToCollectionIfMissing(
      this.dadosPessoaisSharedCollection,
      acompanhamentoAluno.dadosPessoais
    );
  }

  protected loadRelationshipsOptions(): void {
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

  protected createFromForm(): IAcompanhamentoAluno {
    return {
      ...new AcompanhamentoAluno(),
      id: this.editForm.get(['id'])!.value,
      ano: this.editForm.get(['ano'])!.value,
      ensino: this.editForm.get(['ensino'])!.value,
      bimestre: this.editForm.get(['bimestre'])!.value,
      dadosPessoais: this.editForm.get(['dadosPessoais'])!.value,
    };
  }
}
