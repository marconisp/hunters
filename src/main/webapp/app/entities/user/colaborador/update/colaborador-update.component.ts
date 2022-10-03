import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IColaborador, Colaborador } from '../colaborador.model';
import { ColaboradorService } from '../service/colaborador.service';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';
import { DadosPessoaisService } from 'app/entities/user/dados-pessoais/service/dados-pessoais.service';

@Component({
  selector: 'jhi-colaborador-update',
  templateUrl: './colaborador-update.component.html',
})
export class ColaboradorUpdateComponent implements OnInit {
  isSaving = false;

  dadosPessoaisSharedCollection: IDadosPessoais[] = [];

  editForm = this.fb.group({
    id: [],
    dataCadastro: [null, [Validators.required]],
    dataAdmissao: [],
    dataRecisao: [],
    salario: [],
    ativo: [],
    obs: [null, [Validators.maxLength(100)]],
    dadosPessoais1: [],
  });

  constructor(
    protected colaboradorService: ColaboradorService,
    protected dadosPessoaisService: DadosPessoaisService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ colaborador }) => {
      this.updateForm(colaborador);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const colaborador = this.createFromForm();
    if (colaborador.id !== undefined) {
      this.subscribeToSaveResponse(this.colaboradorService.update(colaborador));
    } else {
      this.subscribeToSaveResponse(this.colaboradorService.create(colaborador));
    }
  }

  trackDadosPessoaisById(_index: number, item: IDadosPessoais): any {
    return item.id;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IColaborador>>): void {
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

  protected updateForm(colaborador: IColaborador): void {
    this.editForm.patchValue({
      id: colaborador.id,
      dataCadastro: colaborador.dataCadastro,
      dataAdmissao: colaborador.dataAdmissao,
      dataRecisao: colaborador.dataRecisao,
      salario: colaborador.salario,
      ativo: colaborador.ativo,
      obs: colaborador.obs,
      dadosPessoais: colaborador.dadosPessoais,
    });

    this.dadosPessoaisSharedCollection = this.dadosPessoaisService.addDadosPessoaisToCollectionIfMissing(
      this.dadosPessoaisSharedCollection,
      colaborador.dadosPessoais
    );
  }

  protected loadRelationshipsOptions(): void {
    this.dadosPessoaisService
      .query()
      .pipe(map((res: HttpResponse<IDadosPessoais[]>) => res.body ?? []))
      .pipe(
        map((dadosPessoais: IDadosPessoais[]) =>
          this.dadosPessoaisService.addDadosPessoaisToCollectionIfMissing(dadosPessoais, this.editForm.get('dadosPessoais')?.value)
        )
      )
      .subscribe((dadosPessoais: IDadosPessoais[]) => (this.dadosPessoaisSharedCollection = dadosPessoais));
  }

  protected createFromForm(): IColaborador {
    return {
      ...new Colaborador(),
      id: this.editForm.get(['id'])!.value,
      dataCadastro: this.editForm.get(['dataCadastro'])!.value,
      dataAdmissao: this.editForm.get(['dataAdmissao'])!.value,
      dataRecisao: this.editForm.get(['dataRecisao'])!.value,
      salario: this.editForm.get(['salario'])!.value,
      ativo: this.editForm.get(['ativo'])!.value,
      obs: this.editForm.get(['obs'])!.value,
      dadosPessoais: this.editForm.get(['dadosPessoais'])!.value,
    };
  }
}
