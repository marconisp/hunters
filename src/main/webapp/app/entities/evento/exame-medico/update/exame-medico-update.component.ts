import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IExameMedico, ExameMedico } from '../exame-medico.model';
import { ExameMedicoService } from '../service/exame-medico.service';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';
import { DadosPessoaisService } from 'app/entities/user/dados-pessoais/service/dados-pessoais.service';

@Component({
  selector: 'jhi-exame-medico-update',
  templateUrl: './exame-medico-update.component.html',
})
export class ExameMedicoUpdateComponent implements OnInit {
  isSaving = false;

  dadosPessoaisSharedCollection: IDadosPessoais[] = [];

  editForm = this.fb.group({
    id: [],
    data: [null, [Validators.required]],
    nomeMedico: [null, [Validators.maxLength(50)]],
    crmMedico: [null, [Validators.maxLength(20)]],
    resumo: [null, [Validators.maxLength(200)]],
    obs: [null, [Validators.maxLength(100)]],
    dadosPessoais: [],
  });

  constructor(
    protected exameMedicoService: ExameMedicoService,
    protected dadosPessoaisService: DadosPessoaisService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ exameMedico }) => {
      this.updateForm(exameMedico);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const exameMedico = this.createFromForm();
    if (exameMedico.id !== undefined) {
      this.subscribeToSaveResponse(this.exameMedicoService.update(exameMedico));
    } else {
      this.subscribeToSaveResponse(this.exameMedicoService.create(exameMedico));
    }
  }

  trackDadosPessoaisById(_index: number, item: IDadosPessoais): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExameMedico>>): void {
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

  protected updateForm(exameMedico: IExameMedico): void {
    this.editForm.patchValue({
      id: exameMedico.id,
      data: exameMedico.data,
      nomeMedico: exameMedico.nomeMedico,
      crmMedico: exameMedico.crmMedico,
      resumo: exameMedico.resumo,
      obs: exameMedico.obs,
      dadosPessoais: exameMedico.dadosPessoais,
    });

    this.dadosPessoaisSharedCollection = this.dadosPessoaisService.addDadosPessoaisToCollectionIfMissing(
      this.dadosPessoaisSharedCollection,
      exameMedico.dadosPessoais
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

  protected createFromForm(): IExameMedico {
    return {
      ...new ExameMedico(),
      id: this.editForm.get(['id'])!.value,
      data: this.editForm.get(['data'])!.value,
      nomeMedico: this.editForm.get(['nomeMedico'])!.value,
      crmMedico: this.editForm.get(['crmMedico'])!.value,
      resumo: this.editForm.get(['resumo'])!.value,
      obs: this.editForm.get(['obs'])!.value,
      dadosPessoais: this.editForm.get(['dadosPessoais'])!.value,
    };
  }
}
