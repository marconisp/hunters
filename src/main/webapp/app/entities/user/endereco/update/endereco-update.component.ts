import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEndereco, Endereco } from '../endereco.model';
import { EnderecoService } from '../service/endereco.service';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';
import { DadosPessoaisService } from 'app/entities/user/dados-pessoais/service/dados-pessoais.service';

@Component({
  selector: 'jhi-endereco-update',
  templateUrl: './endereco-update.component.html',
})
export class EnderecoUpdateComponent implements OnInit {
  isSaving = false;

  dadosPessoaisSharedCollection: IDadosPessoais[] = [];

  editForm = this.fb.group({
    id: [],
    cep: [null, [Validators.required, Validators.minLength(8), Validators.maxLength(10)]],
    logradouro: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    complemento1: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    complemento2: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    numero: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(10)]],
    bairro: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    localidade: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    uf: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    unidade: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(20)]],
    ibge: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(20)]],
    gia: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(20)]],
    latitude: [null, [Validators.required]],
    longitude: [null, [Validators.required]],
    dadosPessoais: [],
  });

  constructor(
    protected enderecoService: EnderecoService,
    protected dadosPessoaisService: DadosPessoaisService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ endereco }) => {
      this.updateForm(endereco);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const endereco = this.createFromForm();
    if (endereco.id !== undefined) {
      this.subscribeToSaveResponse(this.enderecoService.update(endereco));
    } else {
      this.subscribeToSaveResponse(this.enderecoService.create(endereco));
    }
  }

  trackDadosPessoaisById(_index: number, item: IDadosPessoais): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEndereco>>): void {
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

  protected updateForm(endereco: IEndereco): void {
    this.editForm.patchValue({
      id: endereco.id,
      cep: endereco.cep,
      logradouro: endereco.logradouro,
      complemento1: endereco.complemento1,
      complemento2: endereco.complemento2,
      numero: endereco.numero,
      bairro: endereco.bairro,
      localidade: endereco.localidade,
      uf: endereco.uf,
      unidade: endereco.unidade,
      ibge: endereco.ibge,
      gia: endereco.gia,
      latitude: endereco.latitude,
      longitude: endereco.longitude,
      dadosPessoais: endereco.dadosPessoais,
    });

    this.dadosPessoaisSharedCollection = this.dadosPessoaisService.addDadosPessoaisToCollectionIfMissing(
      this.dadosPessoaisSharedCollection,
      endereco.dadosPessoais
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

  protected createFromForm(): IEndereco {
    return {
      ...new Endereco(),
      id: this.editForm.get(['id'])!.value,
      cep: this.editForm.get(['cep'])!.value,
      logradouro: this.editForm.get(['logradouro'])!.value,
      complemento1: this.editForm.get(['complemento1'])!.value,
      complemento2: this.editForm.get(['complemento2'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      bairro: this.editForm.get(['bairro'])!.value,
      localidade: this.editForm.get(['localidade'])!.value,
      uf: this.editForm.get(['uf'])!.value,
      unidade: this.editForm.get(['unidade'])!.value,
      ibge: this.editForm.get(['ibge'])!.value,
      gia: this.editForm.get(['gia'])!.value,
      latitude: this.editForm.get(['latitude'])!.value,
      longitude: this.editForm.get(['longitude'])!.value,
      dadosPessoais: this.editForm.get(['dadosPessoais'])!.value,
    };
  }
}
