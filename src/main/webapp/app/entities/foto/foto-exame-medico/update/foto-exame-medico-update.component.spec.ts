import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FotoExameMedicoService } from '../service/foto-exame-medico.service';
import { IFotoExameMedico, FotoExameMedico } from '../foto-exame-medico.model';
import { IExameMedico } from 'app/entities/evento/exame-medico/exame-medico.model';
import { ExameMedicoService } from 'app/entities/evento/exame-medico/service/exame-medico.service';

import { FotoExameMedicoUpdateComponent } from './foto-exame-medico-update.component';

describe('FotoExameMedico Management Update Component', () => {
  let comp: FotoExameMedicoUpdateComponent;
  let fixture: ComponentFixture<FotoExameMedicoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fotoExameMedicoService: FotoExameMedicoService;
  let exameMedicoService: ExameMedicoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FotoExameMedicoUpdateComponent],
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
      .overrideTemplate(FotoExameMedicoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FotoExameMedicoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fotoExameMedicoService = TestBed.inject(FotoExameMedicoService);
    exameMedicoService = TestBed.inject(ExameMedicoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ExameMedico query and add missing value', () => {
      const fotoExameMedico: IFotoExameMedico = { id: 456 };
      const exameMedico: IExameMedico = { id: 95217 };
      fotoExameMedico.exameMedico = exameMedico;

      const exameMedicoCollection: IExameMedico[] = [{ id: 49150 }];
      jest.spyOn(exameMedicoService, 'query').mockReturnValue(of(new HttpResponse({ body: exameMedicoCollection })));
      const additionalExameMedicos = [exameMedico];
      const expectedCollection: IExameMedico[] = [...additionalExameMedicos, ...exameMedicoCollection];
      jest.spyOn(exameMedicoService, 'addExameMedicoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fotoExameMedico });
      comp.ngOnInit();

      expect(exameMedicoService.query).toHaveBeenCalled();
      expect(exameMedicoService.addExameMedicoToCollectionIfMissing).toHaveBeenCalledWith(exameMedicoCollection, ...additionalExameMedicos);
      expect(comp.exameMedicosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const fotoExameMedico: IFotoExameMedico = { id: 456 };
      const exameMedico: IExameMedico = { id: 75472 };
      fotoExameMedico.exameMedico = exameMedico;

      activatedRoute.data = of({ fotoExameMedico });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(fotoExameMedico));
      expect(comp.exameMedicosSharedCollection).toContain(exameMedico);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FotoExameMedico>>();
      const fotoExameMedico = { id: 123 };
      jest.spyOn(fotoExameMedicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoExameMedico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fotoExameMedico }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(fotoExameMedicoService.update).toHaveBeenCalledWith(fotoExameMedico);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FotoExameMedico>>();
      const fotoExameMedico = new FotoExameMedico();
      jest.spyOn(fotoExameMedicoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoExameMedico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fotoExameMedico }));
      saveSubject.complete();

      // THEN
      expect(fotoExameMedicoService.create).toHaveBeenCalledWith(fotoExameMedico);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FotoExameMedico>>();
      const fotoExameMedico = { id: 123 };
      jest.spyOn(fotoExameMedicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoExameMedico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fotoExameMedicoService.update).toHaveBeenCalledWith(fotoExameMedico);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackExameMedicoById', () => {
      it('Should return tracked ExameMedico primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackExameMedicoById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
