import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ExameMedicoService } from '../service/exame-medico.service';
import { IExameMedico, ExameMedico } from '../exame-medico.model';
import { IDadosPessoais1 } from 'app/entities/user/dados-pessoais-1/dados-pessoais-1.model';
import { DadosPessoais1Service } from 'app/entities/user/dados-pessoais-1/service/dados-pessoais-1.service';

import { ExameMedicoUpdateComponent } from './exame-medico-update.component';

describe('ExameMedico Management Update Component', () => {
  let comp: ExameMedicoUpdateComponent;
  let fixture: ComponentFixture<ExameMedicoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let exameMedicoService: ExameMedicoService;
  let dadosPessoais1Service: DadosPessoais1Service;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ExameMedicoUpdateComponent],
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
      .overrideTemplate(ExameMedicoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ExameMedicoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    exameMedicoService = TestBed.inject(ExameMedicoService);
    dadosPessoais1Service = TestBed.inject(DadosPessoais1Service);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DadosPessoais1 query and add missing value', () => {
      const exameMedico: IExameMedico = { id: 456 };
      const dadosPessoais1: IDadosPessoais1 = { id: 65412 };
      exameMedico.dadosPessoais1 = dadosPessoais1;

      const dadosPessoais1Collection: IDadosPessoais1[] = [{ id: 58317 }];
      jest.spyOn(dadosPessoais1Service, 'query').mockReturnValue(of(new HttpResponse({ body: dadosPessoais1Collection })));
      const additionalDadosPessoais1s = [dadosPessoais1];
      const expectedCollection: IDadosPessoais1[] = [...additionalDadosPessoais1s, ...dadosPessoais1Collection];
      jest.spyOn(dadosPessoais1Service, 'addDadosPessoais1ToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ exameMedico });
      comp.ngOnInit();

      expect(dadosPessoais1Service.query).toHaveBeenCalled();
      expect(dadosPessoais1Service.addDadosPessoais1ToCollectionIfMissing).toHaveBeenCalledWith(
        dadosPessoais1Collection,
        ...additionalDadosPessoais1s
      );
      expect(comp.dadosPessoais1sSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const exameMedico: IExameMedico = { id: 456 };
      const dadosPessoais1: IDadosPessoais1 = { id: 66723 };
      exameMedico.dadosPessoais1 = dadosPessoais1;

      activatedRoute.data = of({ exameMedico });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(exameMedico));
      expect(comp.dadosPessoais1sSharedCollection).toContain(dadosPessoais1);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ExameMedico>>();
      const exameMedico = { id: 123 };
      jest.spyOn(exameMedicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ exameMedico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: exameMedico }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(exameMedicoService.update).toHaveBeenCalledWith(exameMedico);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ExameMedico>>();
      const exameMedico = new ExameMedico();
      jest.spyOn(exameMedicoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ exameMedico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: exameMedico }));
      saveSubject.complete();

      // THEN
      expect(exameMedicoService.create).toHaveBeenCalledWith(exameMedico);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ExameMedico>>();
      const exameMedico = { id: 123 };
      jest.spyOn(exameMedicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ exameMedico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(exameMedicoService.update).toHaveBeenCalledWith(exameMedico);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackDadosPessoais1ById', () => {
      it('Should return tracked DadosPessoais1 primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDadosPessoais1ById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
