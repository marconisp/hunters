import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEnderecoEvento, EnderecoEvento } from '../endereco-evento.model';
import { EnderecoEventoService } from '../service/endereco-evento.service';
import { IEvento } from 'app/entities/evento/evento/evento.model';
import { EventoService } from 'app/entities/evento/evento/service/evento.service';

@Component({
  selector: 'jhi-endereco-evento-update',
  templateUrl: './endereco-evento-update.component.html',
})
export class EnderecoEventoUpdateComponent implements OnInit {
  isSaving = false;

  eventosSharedCollection: IEvento[] = [];

  editForm = this.fb.group({
    id: [],
    cep: [null, [Validators.required, Validators.minLength(8), Validators.maxLength(10)]],
    logradouro: [null, [Validators.required, Validators.maxLength(50)]],
    complemento: [null, [Validators.maxLength(50)]],
    numero: [null, [Validators.required, Validators.maxLength(10)]],
    bairro: [null, [Validators.required, Validators.maxLength(50)]],
    cidade: [null, [Validators.required, Validators.maxLength(50)]],
    uf: [null, [Validators.required, Validators.maxLength(2)]],
    evento: [],
  });

  constructor(
    protected enderecoEventoService: EnderecoEventoService,
    protected eventoService: EventoService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ enderecoEvento }) => {
      this.updateForm(enderecoEvento);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const enderecoEvento = this.createFromForm();
    if (enderecoEvento.id !== undefined) {
      this.subscribeToSaveResponse(this.enderecoEventoService.update(enderecoEvento));
    } else {
      this.subscribeToSaveResponse(this.enderecoEventoService.create(enderecoEvento));
    }
  }

  trackEventoById(_index: number, item: IEvento): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnderecoEvento>>): void {
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

  protected updateForm(enderecoEvento: IEnderecoEvento): void {
    this.editForm.patchValue({
      id: enderecoEvento.id,
      cep: enderecoEvento.cep,
      logradouro: enderecoEvento.logradouro,
      complemento: enderecoEvento.complemento,
      numero: enderecoEvento.numero,
      bairro: enderecoEvento.bairro,
      cidade: enderecoEvento.cidade,
      uf: enderecoEvento.uf,
      evento: enderecoEvento.evento,
    });

    this.eventosSharedCollection = this.eventoService.addEventoToCollectionIfMissing(this.eventosSharedCollection, enderecoEvento.evento);
  }

  protected loadRelationshipsOptions(): void {
    this.eventoService
      .query()
      .pipe(map((res: HttpResponse<IEvento[]>) => res.body ?? []))
      .pipe(map((eventos: IEvento[]) => this.eventoService.addEventoToCollectionIfMissing(eventos, this.editForm.get('evento')!.value)))
      .subscribe((eventos: IEvento[]) => (this.eventosSharedCollection = eventos));
  }

  protected createFromForm(): IEnderecoEvento {
    return {
      ...new EnderecoEvento(),
      id: this.editForm.get(['id'])!.value,
      cep: this.editForm.get(['cep'])!.value,
      logradouro: this.editForm.get(['logradouro'])!.value,
      complemento: this.editForm.get(['complemento'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      bairro: this.editForm.get(['bairro'])!.value,
      cidade: this.editForm.get(['cidade'])!.value,
      uf: this.editForm.get(['uf'])!.value,
      evento: this.editForm.get(['evento'])!.value,
    };
  }
}
