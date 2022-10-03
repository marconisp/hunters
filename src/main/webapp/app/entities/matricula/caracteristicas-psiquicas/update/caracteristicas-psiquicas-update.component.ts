import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICaracteristicasPsiquicas, CaracteristicasPsiquicas } from '../caracteristicas-psiquicas.model';
import { CaracteristicasPsiquicasService } from '../service/caracteristicas-psiquicas.service';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';
import { DadosPessoaisService } from 'app/entities/user/dados-pessoais/service/dados-pessoais.service';

@Component({
  selector: 'jhi-caracteristicas-psiquicas-update',
  templateUrl: './caracteristicas-psiquicas-update.component.html',
})
export class CaracteristicasPsiquicasUpdateComponent implements OnInit {
  isSaving = false;

  dadosPessoaisSharedCollection: IDadosPessoais[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(50)]],
    dadosPessoais: [],
  });

  constructor(
    protected caracteristicasPsiquicasService: CaracteristicasPsiquicasService,
    protected dadosPessoaisService: DadosPessoaisService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ caracteristicasPsiquicas }) => {
      this.updateForm(caracteristicasPsiquicas);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const caracteristicasPsiquicas = this.createFromForm();
    if (caracteristicasPsiquicas.id !== undefined) {
      this.subscribeToSaveResponse(this.caracteristicasPsiquicasService.update(caracteristicasPsiquicas));
    } else {
      this.subscribeToSaveResponse(this.caracteristicasPsiquicasService.create(caracteristicasPsiquicas));
    }
  }

  trackDadosPessoaisById(_index: number, item: IDadosPessoais): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICaracteristicasPsiquicas>>): void {
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

  protected updateForm(caracteristicasPsiquicas: ICaracteristicasPsiquicas): void {
    this.editForm.patchValue({
      id: caracteristicasPsiquicas.id,
      nome: caracteristicasPsiquicas.nome,
      dadosPessoais: caracteristicasPsiquicas.dadosPessoais,
    });

    this.dadosPessoaisSharedCollection = this.dadosPessoaisService.addDadosPessoaisToCollectionIfMissing(
      this.dadosPessoaisSharedCollection,
      caracteristicasPsiquicas.dadosPessoais
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

  protected createFromForm(): ICaracteristicasPsiquicas {
    return {
      ...new CaracteristicasPsiquicas(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      dadosPessoais: this.editForm.get(['dadosPessoais'])!.value,
    };
  }
}
