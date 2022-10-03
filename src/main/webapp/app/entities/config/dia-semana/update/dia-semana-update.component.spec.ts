import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DiaSemanaService } from '../service/dia-semana.service';
import { IDiaSemana, DiaSemana } from '../dia-semana.model';
import { IPeriodoDuracao } from 'app/entities/config/periodo-duracao/periodo-duracao.model';
import { PeriodoDuracaoService } from 'app/entities/config/periodo-duracao/service/periodo-duracao.service';

import { DiaSemanaUpdateComponent } from './dia-semana-update.component';

describe('DiaSemana Management Update Component', () => {
  let comp: DiaSemanaUpdateComponent;
  let fixture: ComponentFixture<DiaSemanaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let diaSemanaService: DiaSemanaService;
  let periodoDuracaoService: PeriodoDuracaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DiaSemanaUpdateComponent],
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
      .overrideTemplate(DiaSemanaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DiaSemanaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    diaSemanaService = TestBed.inject(DiaSemanaService);
    periodoDuracaoService = TestBed.inject(PeriodoDuracaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PeriodoDuracao query and add missing value', () => {
      const diaSemana: IDiaSemana = { id: 456 };
      const periodoDuracao: IPeriodoDuracao = { id: 37485 };
      diaSemana.periodoDuracao = periodoDuracao;

      const periodoDuracaoCollection: IPeriodoDuracao[] = [{ id: 84428 }];
      jest.spyOn(periodoDuracaoService, 'query').mockReturnValue(of(new HttpResponse({ body: periodoDuracaoCollection })));
      const additionalPeriodoDuracaos = [periodoDuracao];
      const expectedCollection: IPeriodoDuracao[] = [...additionalPeriodoDuracaos, ...periodoDuracaoCollection];
      jest.spyOn(periodoDuracaoService, 'addPeriodoDuracaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ diaSemana });
      comp.ngOnInit();

      expect(periodoDuracaoService.query).toHaveBeenCalled();
      expect(periodoDuracaoService.addPeriodoDuracaoToCollectionIfMissing).toHaveBeenCalledWith(
        periodoDuracaoCollection,
        ...additionalPeriodoDuracaos
      );
      expect(comp.periodoDuracaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const diaSemana: IDiaSemana = { id: 456 };
      const periodoDuracao: IPeriodoDuracao = { id: 20156 };
      diaSemana.periodoDuracao = periodoDuracao;

      activatedRoute.data = of({ diaSemana });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(diaSemana));
      expect(comp.periodoDuracaosSharedCollection).toContain(periodoDuracao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DiaSemana>>();
      const diaSemana = { id: 123 };
      jest.spyOn(diaSemanaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ diaSemana });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: diaSemana }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(diaSemanaService.update).toHaveBeenCalledWith(diaSemana);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DiaSemana>>();
      const diaSemana = new DiaSemana();
      jest.spyOn(diaSemanaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ diaSemana });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: diaSemana }));
      saveSubject.complete();

      // THEN
      expect(diaSemanaService.create).toHaveBeenCalledWith(diaSemana);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DiaSemana>>();
      const diaSemana = { id: 123 };
      jest.spyOn(diaSemanaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ diaSemana });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(diaSemanaService.update).toHaveBeenCalledWith(diaSemana);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPeriodoDuracaoById', () => {
      it('Should return tracked PeriodoDuracao primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPeriodoDuracaoById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
