import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDocumento, Documento } from '../documento.model';
import { DocumentoService } from '../service/documento.service';
import { ITipoDocumento } from 'app/entities/config/tipo-documento/tipo-documento.model';
import { TipoDocumentoService } from 'app/entities/config/tipo-documento/service/tipo-documento.service';
import { IDadosPessoais, DadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';

@Component({
  selector: 'jhi-documento-update',
  templateUrl: './documento-update.component.html',
})
export class DocumentoUpdateComponent implements OnInit {
  isSaving = false;

  tipoDocumentosCollection: ITipoDocumento[] = [];
  dadosPessoais?: IDadosPessoais;

  editForm = this.fb.group({
    id: [],
    descricao: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    tipoDocumento: [],
  });

  constructor(
    protected documentoService: DocumentoService,
    protected tipoDocumentoService: TipoDocumentoService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documento, dadosPessoais }) => {
      this.dadosPessoais = dadosPessoais ?? new DadosPessoais();
      this.updateForm(documento);
      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const documento = this.createFromForm();
    documento.dadosPessoais = this.dadosPessoais;
    if (documento.id !== undefined) {
      this.subscribeToSaveResponse(this.documentoService.update(documento));
    } else {
      this.subscribeToSaveResponse(this.documentoService.create(documento));
    }
  }

  trackTipoDocumentoById(_index: number, item: ITipoDocumento): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumento>>): void {
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

  protected updateForm(documento: IDocumento): void {
    this.editForm.patchValue({
      id: documento.id,
      descricao: documento.descricao,
      tipoDocumento: documento.tipoDocumento,
    });

    this.tipoDocumentosCollection = this.tipoDocumentoService.addTipoDocumentoToCollectionIfMissing(
      this.tipoDocumentosCollection,
      documento.tipoDocumento
    );
  }

  protected loadRelationshipsOptions(): void {
    this.tipoDocumentoService
      .query({ filter: 'documento-is-null' })
      .pipe(map((res: HttpResponse<ITipoDocumento[]>) => res.body ?? []))
      .pipe(
        map((tipoDocumentos: ITipoDocumento[]) =>
          this.tipoDocumentoService.addTipoDocumentoToCollectionIfMissing(tipoDocumentos, this.editForm.get('tipoDocumento')!.value)
        )
      )
      .subscribe((tipoDocumentos: ITipoDocumento[]) => (this.tipoDocumentosCollection = tipoDocumentos));
  }

  protected createFromForm(): IDocumento {
    return {
      ...new Documento(),
      id: this.editForm.get(['id'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      tipoDocumento: this.editForm.get(['tipoDocumento'])!.value,
    };
  }
}
