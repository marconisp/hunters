import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAvaliacaoEconomica, AvaliacaoEconomica } from '../avaliacao-economica.model';
import { AvaliacaoEconomicaService } from '../service/avaliacao-economica.service';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';
import { DadosPessoaisService } from 'app/entities/user/dados-pessoais/service/dados-pessoais.service';
import { SimNao } from 'app/entities/enumerations/sim-nao.model';
import { Escola } from 'app/entities/enumerations/escola.model';
import { Moradia } from 'app/entities/enumerations/moradia.model';
import { Pais } from 'app/entities/enumerations/pais.model';
import { SituacaoMoradia } from 'app/entities/enumerations/situacao-moradia.model';
import { TipoMoradia } from 'app/entities/enumerations/tipo-moradia.model';
import { FamiliaExiste } from 'app/entities/enumerations/familia-existe.model';
import { AssitenciaMedica } from 'app/entities/enumerations/assitencia-medica.model';

@Component({
  selector: 'jhi-avaliacao-economica-update',
  templateUrl: './avaliacao-economica-update.component.html',
})
export class AvaliacaoEconomicaUpdateComponent implements OnInit {
  isSaving = false;
  simNaoValues = Object.keys(SimNao);
  escolaValues = Object.keys(Escola);
  moradiaValues = Object.keys(Moradia);
  paisValues = Object.keys(Pais);
  situacaoMoradiaValues = Object.keys(SituacaoMoradia);
  tipoMoradiaValues = Object.keys(TipoMoradia);
  familiaExisteValues = Object.keys(FamiliaExiste);
  assitenciaMedicaValues = Object.keys(AssitenciaMedica);

  dadosPessoaisSharedCollection: IDadosPessoais[] = [];

  editForm = this.fb.group({
    id: [],
    trabalhoOuEstagio: [null, [Validators.required]],
    vinculoEmpregaticio: [],
    cargoFuncao: [null, [Validators.maxLength(50)]],
    contribuiRendaFamiliar: [null, [Validators.required]],
    apoioFinanceiroFamiliar: [null, [Validators.required]],
    estudaAtualmente: [null, [Validators.required]],
    escolaAtual: [],
    estudouAnteriormente: [null, [Validators.required]],
    escolaAnterior: [],
    concluiAnoEscolarAnterior: [null, [Validators.required]],
    repetente: [],
    dificuldadeAprendizado: [],
    dificuldadeDisciplina: [null, [Validators.maxLength(150)]],
    moraCom: [null, [Validators.required]],
    pais: [null, [Validators.required]],
    situacaoMoradia: [null, [Validators.required]],
    tipoMoradia: [null, [Validators.required]],
    recebeBeneficioGoverno: [null, [Validators.required]],
    tipoBeneficio: [null, [Validators.maxLength(100)]],
    familiaExiste: [null, [Validators.required]],
    assitenciaMedica: [null, [Validators.required]],
    temAlergia: [null, [Validators.required]],
    temProblemaSaude: [null, [Validators.required]],
    tomaMedicamento: [null, [Validators.required]],
    teveFratura: [null, [Validators.required]],
    teveCirurgia: [null, [Validators.required]],
    temDeficiencia: [null, [Validators.required]],
    temAcompanhamentoMedico: [null, [Validators.required]],
    dadosPessoais1: [],
  });

  constructor(
    protected avaliacaoEconomicaService: AvaliacaoEconomicaService,
    protected dadosPessoaisService: DadosPessoaisService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avaliacaoEconomica }) => {
      this.updateForm(avaliacaoEconomica);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const avaliacaoEconomica = this.createFromForm();
    if (avaliacaoEconomica.id !== undefined) {
      this.subscribeToSaveResponse(this.avaliacaoEconomicaService.update(avaliacaoEconomica));
    } else {
      this.subscribeToSaveResponse(this.avaliacaoEconomicaService.create(avaliacaoEconomica));
    }
  }

  trackDadosPessoaisById(_index: number, item: IDadosPessoais): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAvaliacaoEconomica>>): void {
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

  protected updateForm(avaliacaoEconomica: IAvaliacaoEconomica): void {
    this.editForm.patchValue({
      id: avaliacaoEconomica.id,
      trabalhoOuEstagio: avaliacaoEconomica.trabalhoOuEstagio,
      vinculoEmpregaticio: avaliacaoEconomica.vinculoEmpregaticio,
      cargoFuncao: avaliacaoEconomica.cargoFuncao,
      contribuiRendaFamiliar: avaliacaoEconomica.contribuiRendaFamiliar,
      apoioFinanceiroFamiliar: avaliacaoEconomica.apoioFinanceiroFamiliar,
      estudaAtualmente: avaliacaoEconomica.estudaAtualmente,
      escolaAtual: avaliacaoEconomica.escolaAtual,
      estudouAnteriormente: avaliacaoEconomica.estudouAnteriormente,
      escolaAnterior: avaliacaoEconomica.escolaAnterior,
      concluiAnoEscolarAnterior: avaliacaoEconomica.concluiAnoEscolarAnterior,
      repetente: avaliacaoEconomica.repetente,
      dificuldadeAprendizado: avaliacaoEconomica.dificuldadeAprendizado,
      dificuldadeDisciplina: avaliacaoEconomica.dificuldadeDisciplina,
      moraCom: avaliacaoEconomica.moraCom,
      pais: avaliacaoEconomica.pais,
      situacaoMoradia: avaliacaoEconomica.situacaoMoradia,
      tipoMoradia: avaliacaoEconomica.tipoMoradia,
      recebeBeneficioGoverno: avaliacaoEconomica.recebeBeneficioGoverno,
      tipoBeneficio: avaliacaoEconomica.tipoBeneficio,
      familiaExiste: avaliacaoEconomica.familiaExiste,
      assitenciaMedica: avaliacaoEconomica.assitenciaMedica,
      temAlergia: avaliacaoEconomica.temAlergia,
      temProblemaSaude: avaliacaoEconomica.temProblemaSaude,
      tomaMedicamento: avaliacaoEconomica.tomaMedicamento,
      teveFratura: avaliacaoEconomica.teveFratura,
      teveCirurgia: avaliacaoEconomica.teveCirurgia,
      temDeficiencia: avaliacaoEconomica.temDeficiencia,
      temAcompanhamentoMedico: avaliacaoEconomica.temAcompanhamentoMedico,
      dadosPessoais: avaliacaoEconomica.dadosPessoais,
    });

    this.dadosPessoaisSharedCollection = this.dadosPessoaisService.addDadosPessoaisToCollectionIfMissing(
      this.dadosPessoaisSharedCollection,
      avaliacaoEconomica.dadosPessoais
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

  protected createFromForm(): IAvaliacaoEconomica {
    return {
      ...new AvaliacaoEconomica(),
      id: this.editForm.get(['id'])!.value,
      trabalhoOuEstagio: this.editForm.get(['trabalhoOuEstagio'])!.value,
      vinculoEmpregaticio: this.editForm.get(['vinculoEmpregaticio'])!.value,
      cargoFuncao: this.editForm.get(['cargoFuncao'])!.value,
      contribuiRendaFamiliar: this.editForm.get(['contribuiRendaFamiliar'])!.value,
      apoioFinanceiroFamiliar: this.editForm.get(['apoioFinanceiroFamiliar'])!.value,
      estudaAtualmente: this.editForm.get(['estudaAtualmente'])!.value,
      escolaAtual: this.editForm.get(['escolaAtual'])!.value,
      estudouAnteriormente: this.editForm.get(['estudouAnteriormente'])!.value,
      escolaAnterior: this.editForm.get(['escolaAnterior'])!.value,
      concluiAnoEscolarAnterior: this.editForm.get(['concluiAnoEscolarAnterior'])!.value,
      repetente: this.editForm.get(['repetente'])!.value,
      dificuldadeAprendizado: this.editForm.get(['dificuldadeAprendizado'])!.value,
      dificuldadeDisciplina: this.editForm.get(['dificuldadeDisciplina'])!.value,
      moraCom: this.editForm.get(['moraCom'])!.value,
      pais: this.editForm.get(['pais'])!.value,
      situacaoMoradia: this.editForm.get(['situacaoMoradia'])!.value,
      tipoMoradia: this.editForm.get(['tipoMoradia'])!.value,
      recebeBeneficioGoverno: this.editForm.get(['recebeBeneficioGoverno'])!.value,
      tipoBeneficio: this.editForm.get(['tipoBeneficio'])!.value,
      familiaExiste: this.editForm.get(['familiaExiste'])!.value,
      assitenciaMedica: this.editForm.get(['assitenciaMedica'])!.value,
      temAlergia: this.editForm.get(['temAlergia'])!.value,
      temProblemaSaude: this.editForm.get(['temProblemaSaude'])!.value,
      tomaMedicamento: this.editForm.get(['tomaMedicamento'])!.value,
      teveFratura: this.editForm.get(['teveFratura'])!.value,
      teveCirurgia: this.editForm.get(['teveCirurgia'])!.value,
      temDeficiencia: this.editForm.get(['temDeficiencia'])!.value,
      temAcompanhamentoMedico: this.editForm.get(['temAcompanhamentoMedico'])!.value,
      dadosPessoais: this.editForm.get(['dadosPessoais'])!.value,
    };
  }
}
