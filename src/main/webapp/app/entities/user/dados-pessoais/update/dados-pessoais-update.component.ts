import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IFoto, Foto } from '../../../foto/foto/foto.model';
import { IDadosPessoais, DadosPessoais } from '../dados-pessoais.model';
import { DadosPessoaisService } from '../service/dados-pessoais.service';
import { IEstadoCivil } from '../../../config/estado-civil/estado-civil.model';
import { EstadoCivilService } from '../../../config/estado-civil/service/estado-civil.service';
import { IRaca } from '../../../config/raca/raca.model';
import { RacaService } from '../../../config/raca/service/raca.service';
import { IReligiao } from '../../../config/religiao/religiao.model';
import { ReligiaoService } from '../../../config/religiao/service/religiao.service';
import { TipoPessoaService } from '../../../config/tipo-pessoa/service/tipo-pessoa.service';

import { AlertError } from '../../../../shared/alert/alert-error.model';
import { DataUtils, FileLoadError } from '../../../../core/util/data-util.service';
import { EventManager, EventWithContent } from '../../../../core/util/event-manager.service';
import { ITipoPessoa } from '../../../config/tipo-pessoa/tipo-pessoa.model';

@Component({
  selector: 'jhi-dados-pessoais-update',
  templateUrl: './dados-pessoais-update.component.html',
})
export class DadosPessoaisUpdateComponent implements OnInit {
  isSaving = false;
  isFotoChange = false;

  estadoCivilsCollection: IEstadoCivil[] = [];
  racasCollection: IRaca[] = [];
  religiaosCollection: IReligiao[] = [];
  tipoPessoasCollection: ITipoPessoa[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(20)]],
    sobreNome: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    pai: [null, [Validators.minLength(1), Validators.maxLength(50)]],
    mae: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    telefone: [null, [Validators.minLength(8), Validators.maxLength(20)]],
    celular: [null, [Validators.required, Validators.minLength(8), Validators.maxLength(20)]],
    whatsapp: [null, [Validators.minLength(8), Validators.maxLength(20)]],
    email: [null, [Validators.required, Validators.minLength(9), Validators.maxLength(50)]],
    tipoPessoa: [null, Validators.required],
    estadoCivil: [null, Validators.required],
    raca: [null, Validators.required],
    religiao: [null, Validators.required],
    idFoto: [],
    conteudo: [],
    conteudoContentType: [],
  });

  constructor(
    protected dadosPessoaisService: DadosPessoaisService,
    protected tipoPessoaService: TipoPessoaService,
    protected estadoCivilService: EstadoCivilService,
    protected racaService: RacaService,
    protected religiaoService: ReligiaoService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected elementRef: ElementRef,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dadosPessoais }) => {
      this.updateForm(dadosPessoais);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dadosPessoais = this.createFromForm();

    if (this.isFotoChange) {
      dadosPessoais.foto = this.createFromFoto();
    }

    if (dadosPessoais.id !== undefined) {
      this.subscribeToSaveResponse(this.dadosPessoaisService.update(dadosPessoais));
    } else {
      this.subscribeToSaveResponse(this.dadosPessoaisService.create(dadosPessoais));
    }
  }

  trackTipoPessoaById(_index: number, item: ITipoPessoa): number {
    return item.id!;
  }

  trackEstadoCivilById(_index: number, item: IEstadoCivil): number {
    return item.id!;
  }

  trackRacaById(_index: number, item: IRaca): number {
    return item.id!;
  }

  trackReligiaoById(_index: number, item: IReligiao): number {
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.isFotoChange = true;
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) => this.errorSetFileData(err),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
    this.isFotoChange = true;
  }

  protected errorSetFileData(err: FileLoadError): void {
    this.eventManager.broadcast(new EventWithContent<AlertError>('userApp.error', { ...err, key: 'error.file.' + err.key }));
    this.isFotoChange = false;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDadosPessoais>>): void {
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

  protected updateForm(dadosPessoais: IDadosPessoais): void {
    this.editForm.patchValue({
      id: dadosPessoais.id,
      nome: dadosPessoais.nome,
      sobreNome: dadosPessoais.sobreNome,
      pai: dadosPessoais.pai,
      mae: dadosPessoais.mae,
      telefone: dadosPessoais.telefone,
      celular: dadosPessoais.celular,
      whatsapp: dadosPessoais.whatsapp,
      email: dadosPessoais.email,
      tipoPessoa: dadosPessoais.tipoPessoa,
      estadoCivil: dadosPessoais.estadoCivil,
      raca: dadosPessoais.raca,
      religiao: dadosPessoais.religiao,
      idFoto: dadosPessoais.foto ? dadosPessoais.foto.id : null,
      conteudo: dadosPessoais.foto ? dadosPessoais.foto.conteudo : [null, [Validators.required]],
      conteudoContentType: dadosPessoais.foto ? dadosPessoais.foto.conteudoContentType : [],
    });

    this.tipoPessoasCollection = this.tipoPessoaService.addTipoPessoaToCollectionIfMissing(
      this.tipoPessoasCollection,
      dadosPessoais.tipoPessoa
    );
    this.estadoCivilsCollection = this.estadoCivilService.addEstadoCivilToCollectionIfMissing(
      this.estadoCivilsCollection,
      dadosPessoais.estadoCivil
    );
    this.racasCollection = this.racaService.addRacaToCollectionIfMissing(this.racasCollection, dadosPessoais.raca);
    this.religiaosCollection = this.religiaoService.addReligiaoToCollectionIfMissing(this.religiaosCollection, dadosPessoais.religiao);
  }

  protected loadRelationshipsOptions(): void {
    this.tipoPessoaService
      .query({ filter: 'tipoPessoa-is-null' })
      .pipe(map((res: HttpResponse<ITipoPessoa[]>) => res.body ?? []))
      .pipe(
        map((tipoPessoas: ITipoPessoa[]) =>
          this.tipoPessoaService.addTipoPessoaToCollectionIfMissing(tipoPessoas, this.editForm.get('tipoPessoa')!.value)
        )
      )
      .subscribe((tipoPessoas: ITipoPessoa[]) => (this.tipoPessoasCollection = tipoPessoas));

    this.estadoCivilService
      .query({ filter: 'dadospessoais-is-null' })
      .pipe(map((res: HttpResponse<IEstadoCivil[]>) => res.body ?? []))
      .pipe(
        map((estadoCivils: IEstadoCivil[]) =>
          this.estadoCivilService.addEstadoCivilToCollectionIfMissing(estadoCivils, this.editForm.get('estadoCivil')!.value)
        )
      )
      .subscribe((estadoCivils: IEstadoCivil[]) => (this.estadoCivilsCollection = estadoCivils));

    this.racaService
      .query({ filter: 'dadospessoais-is-null' })
      .pipe(map((res: HttpResponse<IRaca[]>) => res.body ?? []))
      .pipe(map((racas: IRaca[]) => this.racaService.addRacaToCollectionIfMissing(racas, this.editForm.get('raca')!.value)))
      .subscribe((racas: IRaca[]) => (this.racasCollection = racas));

    this.religiaoService
      .query({ filter: 'dadospessoais-is-null' })
      .pipe(map((res: HttpResponse<IReligiao[]>) => res.body ?? []))
      .pipe(
        map((religiaos: IReligiao[]) =>
          this.religiaoService.addReligiaoToCollectionIfMissing(religiaos, this.editForm.get('religiao')!.value)
        )
      )
      .subscribe((religiaos: IReligiao[]) => (this.religiaosCollection = religiaos));
  }

  protected createFromForm(): IDadosPessoais {
    return {
      ...new DadosPessoais(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      sobreNome: this.editForm.get(['sobreNome'])!.value,
      pai: this.editForm.get(['pai'])!.value,
      mae: this.editForm.get(['mae'])!.value,
      telefone: this.editForm.get(['telefone'])!.value,
      celular: this.editForm.get(['celular'])!.value,
      whatsapp: this.editForm.get(['whatsapp'])!.value,
      email: this.editForm.get(['email'])!.value,
      tipoPessoa: this.editForm.get(['tipoPessoa'])!.value,
      estadoCivil: this.editForm.get(['estadoCivil'])!.value,
      raca: this.editForm.get(['raca'])!.value,
      religiao: this.editForm.get(['religiao'])!.value,
    };
  }

  protected createFromFoto(): IFoto {
    return {
      ...new Foto(),
      id: this.editForm.get(['idFoto'])!.value,
      conteudo: this.editForm.get(['conteudo'])!.value,
      conteudoContentType: this.editForm.get(['conteudoContentType'])!.value,
    };
  }
}
