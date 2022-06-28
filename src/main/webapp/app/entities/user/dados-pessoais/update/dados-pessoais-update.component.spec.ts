import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DadosPessoaisService } from '../service/dados-pessoais.service';
import { IDadosPessoais, DadosPessoais } from '../dados-pessoais.model';
import { ITipoPessoa } from 'app/entities/config/tipo-pessoa/tipo-pessoa.model';
import { TipoPessoaService } from 'app/entities/config/tipo-pessoa/service/tipo-pessoa.service';
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

import { DadosPessoaisUpdateComponent } from './dados-pessoais-update.component';

describe('DadosPessoais Management Update Component', () => {
  let comp: DadosPessoaisUpdateComponent;
  let fixture: ComponentFixture<DadosPessoaisUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dadosPessoaisService: DadosPessoaisService;
  let tipoPessoaService: TipoPessoaService;
  let estadoCivilService: EstadoCivilService;
  let racaService: RacaService;
  let religiaoService: ReligiaoService;
  let fotoService: FotoService;
  let fotoAvatarService: FotoAvatarService;
  let fotoIconService: FotoIconService;
  let user1Service: User1Service;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DadosPessoaisUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(DadosPessoaisUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DadosPessoaisUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dadosPessoaisService = TestBed.inject(DadosPessoaisService);
    tipoPessoaService = TestBed.inject(TipoPessoaService);
    estadoCivilService = TestBed.inject(EstadoCivilService);
    racaService = TestBed.inject(RacaService);
    religiaoService = TestBed.inject(ReligiaoService);
    fotoService = TestBed.inject(FotoService);
    fotoAvatarService = TestBed.inject(FotoAvatarService);
    fotoIconService = TestBed.inject(FotoIconService);
    user1Service = TestBed.inject(User1Service);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call tipoPessoa query and add missing value', () => {
      const dadosPessoais: IDadosPessoais = { id: 456 };
      const tipoPessoa: ITipoPessoa = { id: 5731 };
      dadosPessoais.tipoPessoa = tipoPessoa;

      const tipoPessoaCollection: ITipoPessoa[] = [{ id: 41894 }];
      jest.spyOn(tipoPessoaService, 'query').mockReturnValue(of(new HttpResponse({ body: tipoPessoaCollection })));
      const expectedCollection: ITipoPessoa[] = [tipoPessoa, ...tipoPessoaCollection];
      jest.spyOn(tipoPessoaService, 'addTipoPessoaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dadosPessoais });
      comp.ngOnInit();

      expect(tipoPessoaService.query).toHaveBeenCalled();
      expect(tipoPessoaService.addTipoPessoaToCollectionIfMissing).toHaveBeenCalledWith(tipoPessoaCollection, tipoPessoa);
      expect(comp.tipoPessoasCollection).toEqual(expectedCollection);
    });

    it('Should call estadoCivil query and add missing value', () => {
      const dadosPessoais: IDadosPessoais = { id: 456 };
      const estadoCivil: IEstadoCivil = { id: 96595 };
      dadosPessoais.estadoCivil = estadoCivil;

      const estadoCivilCollection: IEstadoCivil[] = [{ id: 34806 }];
      jest.spyOn(estadoCivilService, 'query').mockReturnValue(of(new HttpResponse({ body: estadoCivilCollection })));
      const expectedCollection: IEstadoCivil[] = [estadoCivil, ...estadoCivilCollection];
      jest.spyOn(estadoCivilService, 'addEstadoCivilToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dadosPessoais });
      comp.ngOnInit();

      expect(estadoCivilService.query).toHaveBeenCalled();
      expect(estadoCivilService.addEstadoCivilToCollectionIfMissing).toHaveBeenCalledWith(estadoCivilCollection, estadoCivil);
      expect(comp.estadoCivilsCollection).toEqual(expectedCollection);
    });

    it('Should call raca query and add missing value', () => {
      const dadosPessoais: IDadosPessoais = { id: 456 };
      const raca: IRaca = { id: 50185 };
      dadosPessoais.raca = raca;

      const racaCollection: IRaca[] = [{ id: 37220 }];
      jest.spyOn(racaService, 'query').mockReturnValue(of(new HttpResponse({ body: racaCollection })));
      const expectedCollection: IRaca[] = [raca, ...racaCollection];
      jest.spyOn(racaService, 'addRacaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dadosPessoais });
      comp.ngOnInit();

      expect(racaService.query).toHaveBeenCalled();
      expect(racaService.addRacaToCollectionIfMissing).toHaveBeenCalledWith(racaCollection, raca);
      expect(comp.racasCollection).toEqual(expectedCollection);
    });

    it('Should call religiao query and add missing value', () => {
      const dadosPessoais: IDadosPessoais = { id: 456 };
      const religiao: IReligiao = { id: 24948 };
      dadosPessoais.religiao = religiao;

      const religiaoCollection: IReligiao[] = [{ id: 88246 }];
      jest.spyOn(religiaoService, 'query').mockReturnValue(of(new HttpResponse({ body: religiaoCollection })));
      const expectedCollection: IReligiao[] = [religiao, ...religiaoCollection];
      jest.spyOn(religiaoService, 'addReligiaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dadosPessoais });
      comp.ngOnInit();

      expect(religiaoService.query).toHaveBeenCalled();
      expect(religiaoService.addReligiaoToCollectionIfMissing).toHaveBeenCalledWith(religiaoCollection, religiao);
      expect(comp.religiaosCollection).toEqual(expectedCollection);
    });

    it('Should call foto query and add missing value', () => {
      const dadosPessoais: IDadosPessoais = { id: 456 };
      const foto: IFoto = { id: 79620 };
      dadosPessoais.foto = foto;

      const fotoCollection: IFoto[] = [{ id: 44401 }];
      jest.spyOn(fotoService, 'query').mockReturnValue(of(new HttpResponse({ body: fotoCollection })));
      const expectedCollection: IFoto[] = [foto, ...fotoCollection];
      jest.spyOn(fotoService, 'addFotoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dadosPessoais });
      comp.ngOnInit();

      expect(fotoService.query).toHaveBeenCalled();
      expect(fotoService.addFotoToCollectionIfMissing).toHaveBeenCalledWith(fotoCollection, foto);
      expect(comp.fotosCollection).toEqual(expectedCollection);
    });

    it('Should call fotoAvatar query and add missing value', () => {
      const dadosPessoais: IDadosPessoais = { id: 456 };
      const fotoAvatar: IFotoAvatar = { id: 42864 };
      dadosPessoais.fotoAvatar = fotoAvatar;

      const fotoAvatarCollection: IFotoAvatar[] = [{ id: 73692 }];
      jest.spyOn(fotoAvatarService, 'query').mockReturnValue(of(new HttpResponse({ body: fotoAvatarCollection })));
      const expectedCollection: IFotoAvatar[] = [fotoAvatar, ...fotoAvatarCollection];
      jest.spyOn(fotoAvatarService, 'addFotoAvatarToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dadosPessoais });
      comp.ngOnInit();

      expect(fotoAvatarService.query).toHaveBeenCalled();
      expect(fotoAvatarService.addFotoAvatarToCollectionIfMissing).toHaveBeenCalledWith(fotoAvatarCollection, fotoAvatar);
      expect(comp.fotoAvatarsCollection).toEqual(expectedCollection);
    });

    it('Should call fotoIcon query and add missing value', () => {
      const dadosPessoais: IDadosPessoais = { id: 456 };
      const fotoIcon: IFotoIcon = { id: 82889 };
      dadosPessoais.fotoIcon = fotoIcon;

      const fotoIconCollection: IFotoIcon[] = [{ id: 57419 }];
      jest.spyOn(fotoIconService, 'query').mockReturnValue(of(new HttpResponse({ body: fotoIconCollection })));
      const expectedCollection: IFotoIcon[] = [fotoIcon, ...fotoIconCollection];
      jest.spyOn(fotoIconService, 'addFotoIconToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dadosPessoais });
      comp.ngOnInit();

      expect(fotoIconService.query).toHaveBeenCalled();
      expect(fotoIconService.addFotoIconToCollectionIfMissing).toHaveBeenCalledWith(fotoIconCollection, fotoIcon);
      expect(comp.fotoIconsCollection).toEqual(expectedCollection);
    });

    it('Should call User1 query and add missing value', () => {
      const dadosPessoais: IDadosPessoais = { id: 456 };
      const user: IUser1 = { id: 87890 };
      dadosPessoais.user = user;

      const user1Collection: IUser1[] = [{ id: 59826 }];
      jest.spyOn(user1Service, 'query').mockReturnValue(of(new HttpResponse({ body: user1Collection })));
      const additionalUser1s = [user];
      const expectedCollection: IUser1[] = [...additionalUser1s, ...user1Collection];
      jest.spyOn(user1Service, 'addUser1ToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dadosPessoais });
      comp.ngOnInit();

      expect(user1Service.query).toHaveBeenCalled();
      expect(user1Service.addUser1ToCollectionIfMissing).toHaveBeenCalledWith(user1Collection, ...additionalUser1s);
      expect(comp.user1sSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const dadosPessoais: IDadosPessoais = { id: 456 };
      const tipoPessoa: ITipoPessoa = { id: 79706 };
      dadosPessoais.tipoPessoa = tipoPessoa;
      const estadoCivil: IEstadoCivil = { id: 3663 };
      dadosPessoais.estadoCivil = estadoCivil;
      const raca: IRaca = { id: 58775 };
      dadosPessoais.raca = raca;
      const religiao: IReligiao = { id: 71151 };
      dadosPessoais.religiao = religiao;
      const foto: IFoto = { id: 49122 };
      dadosPessoais.foto = foto;
      const fotoAvatar: IFotoAvatar = { id: 42611 };
      dadosPessoais.fotoAvatar = fotoAvatar;
      const fotoIcon: IFotoIcon = { id: 75172 };
      dadosPessoais.fotoIcon = fotoIcon;
      const user: IUser1 = { id: 51309 };
      dadosPessoais.user = user;

      activatedRoute.data = of({ dadosPessoais });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(dadosPessoais));
      expect(comp.tipoPessoasCollection).toContain(tipoPessoa);
      expect(comp.estadoCivilsCollection).toContain(estadoCivil);
      expect(comp.racasCollection).toContain(raca);
      expect(comp.religiaosCollection).toContain(religiao);
      expect(comp.fotosCollection).toContain(foto);
      expect(comp.fotoAvatarsCollection).toContain(fotoAvatar);
      expect(comp.fotoIconsCollection).toContain(fotoIcon);
      expect(comp.user1sSharedCollection).toContain(user);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DadosPessoais>>();
      const dadosPessoais = { id: 123 };
      jest.spyOn(dadosPessoaisService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dadosPessoais });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dadosPessoais }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(dadosPessoaisService.update).toHaveBeenCalledWith(dadosPessoais);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DadosPessoais>>();
      const dadosPessoais = new DadosPessoais();
      jest.spyOn(dadosPessoaisService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dadosPessoais });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dadosPessoais }));
      saveSubject.complete();

      // THEN
      expect(dadosPessoaisService.create).toHaveBeenCalledWith(dadosPessoais);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DadosPessoais>>();
      const dadosPessoais = { id: 123 };
      jest.spyOn(dadosPessoaisService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dadosPessoais });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dadosPessoaisService.update).toHaveBeenCalledWith(dadosPessoais);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackTipoPessoaById', () => {
      it('Should return tracked TipoPessoa primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTipoPessoaById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackEstadoCivilById', () => {
      it('Should return tracked EstadoCivil primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEstadoCivilById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackRacaById', () => {
      it('Should return tracked Raca primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackRacaById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackReligiaoById', () => {
      it('Should return tracked Religiao primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackReligiaoById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackFotoById', () => {
      it('Should return tracked Foto primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackFotoById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackFotoAvatarById', () => {
      it('Should return tracked FotoAvatar primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackFotoAvatarById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackFotoIconById', () => {
      it('Should return tracked FotoIcon primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackFotoIconById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackUser1ById', () => {
      it('Should return tracked User1 primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackUser1ById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
