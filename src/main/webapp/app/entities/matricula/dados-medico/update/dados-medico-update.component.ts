import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDadosMedico, DadosMedico } from '../dados-medico.model';
import { DadosMedicoService } from '../service/dados-medico.service';
import { IVacina } from 'app/entities/matricula/vacina/vacina.model';
import { VacinaService } from 'app/entities/matricula/vacina/service/vacina.service';
import { IAlergia } from 'app/entities/matricula/alergia/alergia.model';
import { AlergiaService } from 'app/entities/matricula/alergia/service/alergia.service';
import { IDoenca } from 'app/entities/matricula/doenca/doenca.model';
import { DoencaService } from 'app/entities/matricula/doenca/service/doenca.service';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';
import { DadosPessoaisService } from 'app/entities/user/dados-pessoais/service/dados-pessoais.service';
import { Pressao } from 'app/entities/enumerations/pressao.model';
import { Coracao } from 'app/entities/enumerations/coracao.model';

@Component({
  selector: 'jhi-dados-medico-update',
  templateUrl: './dados-medico-update.component.html',
})
export class DadosMedicoUpdateComponent implements OnInit {
  isSaving = false;
  pressaoValues = Object.keys(Pressao);
  coracaoValues = Object.keys(Coracao);

  vacinasCollection: IVacina[] = [];
  alergiasCollection: IAlergia[] = [];
  doencasCollection: IDoenca[] = [];
  dadosPessoaisSharedCollection: IDadosPessoais[] = [];

  editForm = this.fb.group({
    id: [],
    data: [null, [Validators.required]],
    peso: [null, [Validators.required]],
    altura: [null, [Validators.required]],
    pressao: [null, [Validators.required]],
    coracao: [null, [Validators.required]],
    medicacao: [null, [Validators.maxLength(100)]],
    obs: [null, [Validators.maxLength(50)]],
    vacina: [],
    alergia: [],
    doenca: [],
    dadosPessoais: [],
  });

  constructor(
    protected dadosMedicoService: DadosMedicoService,
    protected vacinaService: VacinaService,
    protected alergiaService: AlergiaService,
    protected doencaService: DoencaService,
    protected dadosPessoaisService: DadosPessoaisService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dadosMedico }) => {
      this.updateForm(dadosMedico);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dadosMedico = this.createFromForm();
    if (dadosMedico.id !== undefined) {
      this.subscribeToSaveResponse(this.dadosMedicoService.update(dadosMedico));
    } else {
      this.subscribeToSaveResponse(this.dadosMedicoService.create(dadosMedico));
    }
  }

  trackVacinaById(_index: number, item: IVacina): number {
    return item.id!;
  }

  trackAlergiaById(_index: number, item: IAlergia): number {
    return item.id!;
  }

  trackDoencaById(_index: number, item: IDoenca): number {
    return item.id!;
  }

  trackDadosPessoaisById(_index: number, item: IDadosPessoais): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDadosMedico>>): void {
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

  protected updateForm(dadosMedico: IDadosMedico): void {
    this.editForm.patchValue({
      id: dadosMedico.id,
      data: dadosMedico.data,
      peso: dadosMedico.peso,
      altura: dadosMedico.altura,
      pressao: dadosMedico.pressao,
      coracao: dadosMedico.coracao,
      medicacao: dadosMedico.medicacao,
      obs: dadosMedico.obs,
      vacina: dadosMedico.vacina,
      alergia: dadosMedico.alergia,
      doenca: dadosMedico.doenca,
      dadosPessoais: dadosMedico.dadosPessoais,
    });

    this.vacinasCollection = this.vacinaService.addVacinaToCollectionIfMissing(this.vacinasCollection, dadosMedico.vacina);
    this.alergiasCollection = this.alergiaService.addAlergiaToCollectionIfMissing(this.alergiasCollection, dadosMedico.alergia);
    this.doencasCollection = this.doencaService.addDoencaToCollectionIfMissing(this.doencasCollection, dadosMedico.doenca);
    this.dadosPessoaisSharedCollection = this.dadosPessoaisService.addDadosPessoaisToCollectionIfMissing(
      this.dadosPessoaisSharedCollection,
      dadosMedico.dadosPessoais
    );
  }

  protected loadRelationshipsOptions(): void {
    this.vacinaService
      .query({ filter: 'dadosmedico-is-null' })
      .pipe(map((res: HttpResponse<IVacina[]>) => res.body ?? []))
      .pipe(map((vacinas: IVacina[]) => this.vacinaService.addVacinaToCollectionIfMissing(vacinas, this.editForm.get('vacina')!.value)))
      .subscribe((vacinas: IVacina[]) => (this.vacinasCollection = vacinas));

    this.alergiaService
      .query({ filter: 'dadosmedico-is-null' })
      .pipe(map((res: HttpResponse<IAlergia[]>) => res.body ?? []))
      .pipe(
        map((alergias: IAlergia[]) => this.alergiaService.addAlergiaToCollectionIfMissing(alergias, this.editForm.get('alergia')!.value))
      )
      .subscribe((alergias: IAlergia[]) => (this.alergiasCollection = alergias));

    this.doencaService
      .query({ filter: 'dadosmedico-is-null' })
      .pipe(map((res: HttpResponse<IDoenca[]>) => res.body ?? []))
      .pipe(map((doencas: IDoenca[]) => this.doencaService.addDoencaToCollectionIfMissing(doencas, this.editForm.get('doenca')!.value)))
      .subscribe((doencas: IDoenca[]) => (this.doencasCollection = doencas));

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

  protected createFromForm(): IDadosMedico {
    return {
      ...new DadosMedico(),
      id: this.editForm.get(['id'])!.value,
      data: this.editForm.get(['data'])!.value,
      peso: this.editForm.get(['peso'])!.value,
      altura: this.editForm.get(['altura'])!.value,
      pressao: this.editForm.get(['pressao'])!.value,
      coracao: this.editForm.get(['coracao'])!.value,
      medicacao: this.editForm.get(['medicacao'])!.value,
      obs: this.editForm.get(['obs'])!.value,
      vacina: this.editForm.get(['vacina'])!.value,
      alergia: this.editForm.get(['alergia'])!.value,
      doenca: this.editForm.get(['doenca'])!.value,
      dadosPessoais: this.editForm.get(['dadosPessoais'])!.value,
    };
  }
}
