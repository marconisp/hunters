import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDadosPessoais, DadosPessoais } from '../dados-pessoais.model';
import { DadosPessoaisService } from '../service/dados-pessoais.service';
import { IEstadoCivil } from 'app/entities/config/estado-civil/estado-civil.model';
import { EstadoCivilService } from 'app/entities/config/estado-civil/service/estado-civil.service';
import { IRaca } from 'app/entities/config/raca/raca.model';
import { RacaService } from 'app/entities/config/raca/service/raca.service';
import { IReligiao } from 'app/entities/config/religiao/religiao.model';
import { ReligiaoService } from 'app/entities/config/religiao/service/religiao.service';
import { IFoto } from 'app/entities/foto/foto/foto.model';
import { FotoService } from 'app/entities/foto/foto/service/foto.service';
import { IFotoAvatar } from 'app/entities/foto/foto-avatar/foto-avatar.model';
import { FotoAvatarService } from 'app/entities/foto/foto-avatar/service/foto-avatar.service';
import { IFotoIcon } from 'app/entities/foto/foto-icon/foto-icon.model';
import { FotoIconService } from 'app/entities/foto/foto-icon/service/foto-icon.service';
import { IUser1 } from 'app/entities/user/user-1/user-1.model';
import { User1Service } from 'app/entities/user/user-1/service/user-1.service';

@Component({
  selector: 'jhi-dados-pessoais-update',
  templateUrl: './dados-pessoais-update.component.html',
})
export class DadosPessoaisUpdateComponent implements OnInit {
  isSaving = false;

  estadoCivilsCollection: IEstadoCivil[] = [];
  racasCollection: IRaca[] = [];
  religiaosCollection: IReligiao[] = [];
  fotosCollection: IFoto[] = [];
  fotoAvatarsCollection: IFotoAvatar[] = [];
  fotoIconsCollection: IFotoIcon[] = [];
  user1sSharedCollection: IUser1[] = [];

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
    estadoCivil: [null, Validators.required],
    raca: [null, Validators.required],
    religiao: [null, Validators.required],
    foto: [],
    fotoAvatar: [],
    fotoIcon: [],
    user: [],
  });

  constructor(
    protected dadosPessoaisService: DadosPessoaisService,
    protected estadoCivilService: EstadoCivilService,
    protected racaService: RacaService,
    protected religiaoService: ReligiaoService,
    protected fotoService: FotoService,
    protected fotoAvatarService: FotoAvatarService,
    protected fotoIconService: FotoIconService,
    protected user1Service: User1Service,
    protected activatedRoute: ActivatedRoute,
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
    if (dadosPessoais.id !== undefined) {
      this.subscribeToSaveResponse(this.dadosPessoaisService.update(dadosPessoais));
    } else {
      this.subscribeToSaveResponse(this.dadosPessoaisService.create(dadosPessoais));
    }
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

  trackFotoById(_index: number, item: IFoto): number {
    return item.id!;
  }

  trackFotoAvatarById(_index: number, item: IFotoAvatar): number {
    return item.id!;
  }

  trackFotoIconById(_index: number, item: IFotoIcon): number {
    return item.id!;
  }

  trackUser1ById(_index: number, item: IUser1): number {
    return item.id!;
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
      estadoCivil: dadosPessoais.estadoCivil,
      raca: dadosPessoais.raca,
      religiao: dadosPessoais.religiao,
      foto: dadosPessoais.foto,
      fotoAvatar: dadosPessoais.fotoAvatar,
      fotoIcon: dadosPessoais.fotoIcon,
      user: dadosPessoais.user,
    });

    this.estadoCivilsCollection = this.estadoCivilService.addEstadoCivilToCollectionIfMissing(
      this.estadoCivilsCollection,
      dadosPessoais.estadoCivil
    );
    this.racasCollection = this.racaService.addRacaToCollectionIfMissing(this.racasCollection, dadosPessoais.raca);
    this.religiaosCollection = this.religiaoService.addReligiaoToCollectionIfMissing(this.religiaosCollection, dadosPessoais.religiao);
    this.fotosCollection = this.fotoService.addFotoToCollectionIfMissing(this.fotosCollection, dadosPessoais.foto);
    this.fotoAvatarsCollection = this.fotoAvatarService.addFotoAvatarToCollectionIfMissing(
      this.fotoAvatarsCollection,
      dadosPessoais.fotoAvatar
    );
    this.fotoIconsCollection = this.fotoIconService.addFotoIconToCollectionIfMissing(this.fotoIconsCollection, dadosPessoais.fotoIcon);
    this.user1sSharedCollection = this.user1Service.addUser1ToCollectionIfMissing(this.user1sSharedCollection, dadosPessoais.user);
  }

  protected loadRelationshipsOptions(): void {
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

    this.fotoService
      .query({ filter: 'dadospessoais-is-null' })
      .pipe(map((res: HttpResponse<IFoto[]>) => res.body ?? []))
      .pipe(map((fotos: IFoto[]) => this.fotoService.addFotoToCollectionIfMissing(fotos, this.editForm.get('foto')!.value)))
      .subscribe((fotos: IFoto[]) => (this.fotosCollection = fotos));

    this.fotoAvatarService
      .query({ filter: 'dadospessoais-is-null' })
      .pipe(map((res: HttpResponse<IFotoAvatar[]>) => res.body ?? []))
      .pipe(
        map((fotoAvatars: IFotoAvatar[]) =>
          this.fotoAvatarService.addFotoAvatarToCollectionIfMissing(fotoAvatars, this.editForm.get('fotoAvatar')!.value)
        )
      )
      .subscribe((fotoAvatars: IFotoAvatar[]) => (this.fotoAvatarsCollection = fotoAvatars));

    this.fotoIconService
      .query({ filter: 'dadospessoais-is-null' })
      .pipe(map((res: HttpResponse<IFotoIcon[]>) => res.body ?? []))
      .pipe(
        map((fotoIcons: IFotoIcon[]) =>
          this.fotoIconService.addFotoIconToCollectionIfMissing(fotoIcons, this.editForm.get('fotoIcon')!.value)
        )
      )
      .subscribe((fotoIcons: IFotoIcon[]) => (this.fotoIconsCollection = fotoIcons));

    this.user1Service
      .query()
      .pipe(map((res: HttpResponse<IUser1[]>) => res.body ?? []))
      .pipe(map((user1s: IUser1[]) => this.user1Service.addUser1ToCollectionIfMissing(user1s, this.editForm.get('user')!.value)))
      .subscribe((user1s: IUser1[]) => (this.user1sSharedCollection = user1s));
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
      estadoCivil: this.editForm.get(['estadoCivil'])!.value,
      raca: this.editForm.get(['raca'])!.value,
      religiao: this.editForm.get(['religiao'])!.value,
      foto: this.editForm.get(['foto'])!.value,
      fotoAvatar: this.editForm.get(['fotoAvatar'])!.value,
      fotoIcon: this.editForm.get(['fotoIcon'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }
}
