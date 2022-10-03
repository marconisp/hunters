import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IEndereco, Endereco } from '../endereco.model';
import { EnderecoService } from '../service/endereco.service';
import { IDadosPessoais, DadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';

@Component({
  selector: 'jhi-endereco-update',
  templateUrl: './endereco-update.component.html',
})
export class EnderecoUpdateComponent implements OnInit {
  isSaving = false;

  dadosPessoais?: IDadosPessoais;

  editForm = this.fb.group({
    id: [],
    cep: [null, [Validators.minLength(8), Validators.maxLength(10)]],
    logradouro: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    complemento1: [null, [Validators.minLength(1), Validators.maxLength(50)]],
    numero: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(10)]],
    bairro: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    localidade: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    uf: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
  });

  constructor(protected enderecoService: EnderecoService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ endereco, dadosPessoais }) => {
      this.dadosPessoais = dadosPessoais ?? new DadosPessoais();
      this.updateForm(endereco);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const endereco = this.createFromForm();
    endereco.dadosPessoais = this.dadosPessoais;
    if (endereco.id !== undefined) {
      this.subscribeToSaveResponse(this.enderecoService.update(endereco));
    } else {
      this.subscribeToSaveResponse(this.enderecoService.create(endereco));
    }
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
      numero: endereco.numero,
      bairro: endereco.bairro,
      localidade: endereco.localidade,
      uf: endereco.uf,
    });
  }

  protected createFromForm(): IEndereco {
    return {
      ...new Endereco(),
      id: this.editForm.get(['id'])!.value,
      cep: this.editForm.get(['cep'])!.value,
      logradouro: this.editForm.get(['logradouro'])!.value,
      complemento1: this.editForm.get(['complemento1'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      bairro: this.editForm.get(['bairro'])!.value,
      localidade: this.editForm.get(['localidade'])!.value,
      uf: this.editForm.get(['uf'])!.value,
    };
  }
}
