import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IReceberDe, ReceberDe } from '../receber-de.model';
import { ReceberDeService } from '../service/receber-de.service';

@Component({
  selector: 'jhi-receber-de-update',
  templateUrl: './receber-de-update.component.html',
})
export class ReceberDeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(50)]],
    descricao: [null, [Validators.maxLength(100)]],
    cnpj: [],
    documento: [null, [Validators.maxLength(20)]],
  });

  constructor(protected receberDeService: ReceberDeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ receberDe }) => {
      this.updateForm(receberDe);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const receberDe = this.createFromForm();
    if (receberDe.id !== undefined) {
      this.subscribeToSaveResponse(this.receberDeService.update(receberDe));
    } else {
      this.subscribeToSaveResponse(this.receberDeService.create(receberDe));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReceberDe>>): void {
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

  protected updateForm(receberDe: IReceberDe): void {
    this.editForm.patchValue({
      id: receberDe.id,
      nome: receberDe.nome,
      descricao: receberDe.descricao,
      cnpj: receberDe.cnpj,
      documento: receberDe.documento,
    });
  }

  protected createFromForm(): IReceberDe {
    return {
      ...new ReceberDe(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      cnpj: this.editForm.get(['cnpj'])!.value,
      documento: this.editForm.get(['documento'])!.value,
    };
  }
}
