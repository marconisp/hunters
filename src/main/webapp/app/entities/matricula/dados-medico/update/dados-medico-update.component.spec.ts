import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DadosMedicoService } from '../service/dados-medico.service';
import { IDadosMedico, DadosMedico } from '../dados-medico.model';
import { IVacina } from 'app/entities/matricula/vacina/vacina.model';
import { VacinaService } from 'app/entities/matricula/vacina/service/vacina.service';
import { IAlergia } from 'app/entities/matricula/alergia/alergia.model';
import { AlergiaService } from 'app/entities/matricula/alergia/service/alergia.service';
import { IDoenca } from 'app/entities/matricula/doenca/doenca.model';
import { DoencaService } from 'app/entities/matricula/doenca/service/doenca.service';
import { IDadosPessoais1 } from 'app/entities/user/dados-pessoais-1/dados-pessoais-1.model';
import { DadosPessoais1Service } from 'app/entities/user/dados-pessoais-1/service/dados-pessoais-1.service';

import { DadosMedicoUpdateComponent } from './dados-medico-update.component';

describe('DadosMedico Management Update Component', () => {
  let comp: DadosMedicoUpdateComponent;
  let fixture: ComponentFixture<DadosMedicoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dadosMedicoService: DadosMedicoService;
  let vacinaService: VacinaService;
  let alergiaService: AlergiaService;
  let doencaService: DoencaService;
  let dadosPessoais1Service: DadosPessoais1Service;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DadosMedicoUpdateComponent],
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
      .overrideTemplate(DadosMedicoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DadosMedicoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dadosMedicoService = TestBed.inject(DadosMedicoService);
    vacinaService = TestBed.inject(VacinaService);
    alergiaService = TestBed.inject(AlergiaService);
    doencaService = TestBed.inject(DoencaService);
    dadosPessoais1Service = TestBed.inject(DadosPessoais1Service);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call vacina query and add missing value', () => {
      const dadosMedico: IDadosMedico = { id: 456 };
      const vacina: IVacina = { id: 83810 };
      dadosMedico.vacina = vacina;

      const vacinaCollection: IVacina[] = [{ id: 58550 }];
      jest.spyOn(vacinaService, 'query').mockReturnValue(of(new HttpResponse({ body: vacinaCollection })));
      const expectedCollection: IVacina[] = [vacina, ...vacinaCollection];
      jest.spyOn(vacinaService, 'addVacinaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dadosMedico });
      comp.ngOnInit();

      expect(vacinaService.query).toHaveBeenCalled();
      expect(vacinaService.addVacinaToCollectionIfMissing).toHaveBeenCalledWith(vacinaCollection, vacina);
      expect(comp.vacinasCollection).toEqual(expectedCollection);
    });

    it('Should call alergia query and add missing value', () => {
      const dadosMedico: IDadosMedico = { id: 456 };
      const alergia: IAlergia = { id: 91778 };
      dadosMedico.alergia = alergia;

      const alergiaCollection: IAlergia[] = [{ id: 88294 }];
      jest.spyOn(alergiaService, 'query').mockReturnValue(of(new HttpResponse({ body: alergiaCollection })));
      const expectedCollection: IAlergia[] = [alergia, ...alergiaCollection];
      jest.spyOn(alergiaService, 'addAlergiaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dadosMedico });
      comp.ngOnInit();

      expect(alergiaService.query).toHaveBeenCalled();
      expect(alergiaService.addAlergiaToCollectionIfMissing).toHaveBeenCalledWith(alergiaCollection, alergia);
      expect(comp.alergiasCollection).toEqual(expectedCollection);
    });

    it('Should call doenca query and add missing value', () => {
      const dadosMedico: IDadosMedico = { id: 456 };
      const doenca: IDoenca = { id: 1658 };
      dadosMedico.doenca = doenca;

      const doencaCollection: IDoenca[] = [{ id: 42628 }];
      jest.spyOn(doencaService, 'query').mockReturnValue(of(new HttpResponse({ body: doencaCollection })));
      const expectedCollection: IDoenca[] = [doenca, ...doencaCollection];
      jest.spyOn(doencaService, 'addDoencaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dadosMedico });
      comp.ngOnInit();

      expect(doencaService.query).toHaveBeenCalled();
      expect(doencaService.addDoencaToCollectionIfMissing).toHaveBeenCalledWith(doencaCollection, doenca);
      expect(comp.doencasCollection).toEqual(expectedCollection);
    });

    it('Should call DadosPessoais1 query and add missing value', () => {
      const dadosMedico: IDadosMedico = { id: 456 };
      const dadosPessoais1: IDadosPessoais1 = { id: 66585 };
      dadosMedico.dadosPessoais1 = dadosPessoais1;

      const dadosPessoais1Collection: IDadosPessoais1[] = [{ id: 90442 }];
      jest.spyOn(dadosPessoais1Service, 'query').mockReturnValue(of(new HttpResponse({ body: dadosPessoais1Collection })));
      const additionalDadosPessoais1s = [dadosPessoais1];
      const expectedCollection: IDadosPessoais1[] = [...additionalDadosPessoais1s, ...dadosPessoais1Collection];
      jest.spyOn(dadosPessoais1Service, 'addDadosPessoais1ToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dadosMedico });
      comp.ngOnInit();

      expect(dadosPessoais1Service.query).toHaveBeenCalled();
      expect(dadosPessoais1Service.addDadosPessoais1ToCollectionIfMissing).toHaveBeenCalledWith(
        dadosPessoais1Collection,
        ...additionalDadosPessoais1s
      );
      expect(comp.dadosPessoais1sSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const dadosMedico: IDadosMedico = { id: 456 };
      const vacina: IVacina = { id: 98 };
      dadosMedico.vacina = vacina;
      const alergia: IAlergia = { id: 5501 };
      dadosMedico.alergia = alergia;
      const doenca: IDoenca = { id: 18057 };
      dadosMedico.doenca = doenca;
      const dadosPessoais1: IDadosPessoais1 = { id: 37994 };
      dadosMedico.dadosPessoais1 = dadosPessoais1;

      activatedRoute.data = of({ dadosMedico });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(dadosMedico));
      expect(comp.vacinasCollection).toContain(vacina);
      expect(comp.alergiasCollection).toContain(alergia);
      expect(comp.doencasCollection).toContain(doenca);
      expect(comp.dadosPessoais1sSharedCollection).toContain(dadosPessoais1);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DadosMedico>>();
      const dadosMedico = { id: 123 };
      jest.spyOn(dadosMedicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dadosMedico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dadosMedico }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(dadosMedicoService.update).toHaveBeenCalledWith(dadosMedico);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DadosMedico>>();
      const dadosMedico = new DadosMedico();
      jest.spyOn(dadosMedicoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dadosMedico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dadosMedico }));
      saveSubject.complete();

      // THEN
      expect(dadosMedicoService.create).toHaveBeenCalledWith(dadosMedico);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DadosMedico>>();
      const dadosMedico = { id: 123 };
      jest.spyOn(dadosMedicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dadosMedico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dadosMedicoService.update).toHaveBeenCalledWith(dadosMedico);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackVacinaById', () => {
      it('Should return tracked Vacina primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackVacinaById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackAlergiaById', () => {
      it('Should return tracked Alergia primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAlergiaById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackDoencaById', () => {
      it('Should return tracked Doenca primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDoencaById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackDadosPessoais1ById', () => {
      it('Should return tracked DadosPessoais1 primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDadosPessoais1ById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
