import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPagarPara, PagarPara } from '../pagar-para.model';
import { PagarParaService } from '../service/pagar-para.service';

@Component({
  selector: 'jhi-pagar-para-update',
  templateUrl: './pagar-para-update.component.html',
})
export class PagarParaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(50)]],
    descricao: [null, [Validators.maxLength(100)]],
    cnpj: [],
    documento: [null, [Validators.maxLength(20)]],
    banco: [null, [Validators.maxLength(50)]],
    agencia: [null, [Validators.maxLength(20)]],
    conta: [null, [Validators.maxLength(20)]],
    pix: [null, [Validators.maxLength(50)]],
  });

  constructor(protected pagarParaService: PagarParaService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pagarPara }) => {
      this.updateForm(pagarPara);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pagarPara = this.createFromForm();
    if (pagarPara.id !== undefined) {
      this.subscribeToSaveResponse(this.pagarParaService.update(pagarPara));
    } else {
      this.subscribeToSaveResponse(this.pagarParaService.create(pagarPara));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPagarPara>>): void {
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

  protected updateForm(pagarPara: IPagarPara): void {
    this.editForm.patchValue({
      id: pagarPara.id,
      nome: pagarPara.nome,
      descricao: pagarPara.descricao,
      cnpj: pagarPara.cnpj,
      documento: pagarPara.documento,
      banco: pagarPara.banco,
      agencia: pagarPara.agencia,
      conta: pagarPara.conta,
      pix: pagarPara.pix,
    });
  }

  protected createFromForm(): IPagarPara {
    return {
      ...new PagarPara(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      cnpj: this.editForm.get(['cnpj'])!.value,
      documento: this.editForm.get(['documento'])!.value,
      banco: this.editForm.get(['banco'])!.value,
      agencia: this.editForm.get(['agencia'])!.value,
      conta: this.editForm.get(['conta'])!.value,
      pix: this.editForm.get(['pix'])!.value,
    };
  }
}
