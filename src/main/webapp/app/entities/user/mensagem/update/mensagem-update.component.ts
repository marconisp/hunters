import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IMensagem, Mensagem } from '../mensagem.model';
import { MensagemService } from '../service/mensagem.service';
import { ITipoMensagem } from 'app/entities/config/tipo-mensagem/tipo-mensagem.model';
import { TipoMensagemService } from 'app/entities/config/tipo-mensagem/service/tipo-mensagem.service';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';
import { DadosPessoaisService } from 'app/entities/user/dados-pessoais/service/dados-pessoais.service';

@Component({
  selector: 'jhi-mensagem-update',
  templateUrl: './mensagem-update.component.html',
})
export class MensagemUpdateComponent implements OnInit {
  isSaving = false;

  tiposCollection: ITipoMensagem[] = [];
  dadosPessoaisSharedCollection: IDadosPessoais[] = [];

  editForm = this.fb.group({
    id: [],
    data: [null, [Validators.required]],
    titulo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(40)]],
    conteudo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(1000)]],
    tipo: [null, Validators.required],
    dadosPessoais: [],
  });

  constructor(
    protected mensagemService: MensagemService,
    protected tipoMensagemService: TipoMensagemService,
    protected dadosPessoaisService: DadosPessoaisService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mensagem }) => {
      this.updateForm(mensagem);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mensagem = this.createFromForm();
    if (mensagem.id !== undefined) {
      this.subscribeToSaveResponse(this.mensagemService.update(mensagem));
    } else {
      this.subscribeToSaveResponse(this.mensagemService.create(mensagem));
    }
  }

  trackTipoMensagemById(_index: number, item: ITipoMensagem): number {
    return item.id!;
  }

  trackDadosPessoaisById(_index: number, item: IDadosPessoais): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMensagem>>): void {
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

  protected updateForm(mensagem: IMensagem): void {
    this.editForm.patchValue({
      id: mensagem.id,
      data: mensagem.data,
      titulo: mensagem.titulo,
      conteudo: mensagem.conteudo,
      tipo: mensagem.tipo,
      dadosPessoais: mensagem.dadosPessoais,
    });

    this.tiposCollection = this.tipoMensagemService.addTipoMensagemToCollectionIfMissing(this.tiposCollection, mensagem.tipo);
    this.dadosPessoaisSharedCollection = this.dadosPessoaisService.addDadosPessoaisToCollectionIfMissing(
      this.dadosPessoaisSharedCollection,
      mensagem.dadosPessoais
    );
  }

  protected loadRelationshipsOptions(): void {
    this.tipoMensagemService
      .query({ filter: 'mensagem-is-null' })
      .pipe(map((res: HttpResponse<ITipoMensagem[]>) => res.body ?? []))
      .pipe(
        map((tipoMensagems: ITipoMensagem[]) =>
          this.tipoMensagemService.addTipoMensagemToCollectionIfMissing(tipoMensagems, this.editForm.get('tipo')!.value)
        )
      )
      .subscribe((tipoMensagems: ITipoMensagem[]) => (this.tiposCollection = tipoMensagems));

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

  protected createFromForm(): IMensagem {
    return {
      ...new Mensagem(),
      id: this.editForm.get(['id'])!.value,
      data: this.editForm.get(['data'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      conteudo: this.editForm.get(['conteudo'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      dadosPessoais: this.editForm.get(['dadosPessoais'])!.value,
    };
  }
}
