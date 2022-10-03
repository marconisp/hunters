import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ITipoContratacao, TipoContratacao } from '../tipo-contratacao.model';
import { TipoContratacaoService } from '../service/tipo-contratacao.service';
import { IColaborador } from 'app/entities/user/colaborador/colaborador.model';
import { ColaboradorService } from 'app/entities/user/colaborador/service/colaborador.service';

@Component({
  selector: 'jhi-tipo-contratacao-update',
  templateUrl: './tipo-contratacao-update.component.html',
})
export class TipoContratacaoUpdateComponent implements OnInit {
  isSaving = false;

  colaboradorsSharedCollection: IColaborador[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(50)]],
    colaborador: [],
  });

  constructor(
    protected tipoContratacaoService: TipoContratacaoService,
    protected colaboradorService: ColaboradorService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoContratacao }) => {
      this.updateForm(tipoContratacao);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoContratacao = this.createFromForm();
    if (tipoContratacao.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoContratacaoService.update(tipoContratacao));
    } else {
      this.subscribeToSaveResponse(this.tipoContratacaoService.create(tipoContratacao));
    }
  }

  trackColaboradorById(_index: number, item: IColaborador): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoContratacao>>): void {
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

  protected updateForm(tipoContratacao: ITipoContratacao): void {
    this.editForm.patchValue({
      id: tipoContratacao.id,
      nome: tipoContratacao.nome,
      colaborador: tipoContratacao.colaborador,
    });

    this.colaboradorsSharedCollection = this.colaboradorService.addColaboradorToCollectionIfMissing(
      this.colaboradorsSharedCollection,
      tipoContratacao.colaborador
    );
  }

  protected loadRelationshipsOptions(): void {
    this.colaboradorService
      .query()
      .pipe(map((res: HttpResponse<IColaborador[]>) => res.body ?? []))
      .pipe(
        map((colaboradors: IColaborador[]) =>
          this.colaboradorService.addColaboradorToCollectionIfMissing(colaboradors, this.editForm.get('colaborador')!.value)
        )
      )
      .subscribe((colaboradors: IColaborador[]) => (this.colaboradorsSharedCollection = colaboradors));
  }

  protected createFromForm(): ITipoContratacao {
    return {
      ...new TipoContratacao(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      colaborador: this.editForm.get(['colaborador'])!.value,
    };
  }
}
