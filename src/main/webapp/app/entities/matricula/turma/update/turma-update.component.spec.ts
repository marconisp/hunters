import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TurmaService } from '../service/turma.service';
import { ITurma, Turma } from '../turma.model';
import { IPeriodoDuracao } from 'app/entities/config/periodo-duracao/periodo-duracao.model';
import { PeriodoDuracaoService } from 'app/entities/config/periodo-duracao/service/periodo-duracao.service';

import { TurmaUpdateComponent } from './turma-update.component';

describe('Turma Management Update Component', () => {
  let comp: TurmaUpdateComponent;
  let fixture: ComponentFixture<TurmaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let turmaService: TurmaService;
  let periodoDuracaoService: PeriodoDuracaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TurmaUpdateComponent],
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
      .overrideTemplate(TurmaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TurmaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    turmaService = TestBed.inject(TurmaService);
    periodoDuracaoService = TestBed.inject(PeriodoDuracaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call periodoDuracao query and add missing value', () => {
      const turma: ITurma = { id: 456 };
      const periodoDuracao: IPeriodoDuracao = { id: 36674 };
      turma.periodoDuracao = periodoDuracao;

      const periodoDuracaoCollection: IPeriodoDuracao[] = [{ id: 5027 }];
      jest.spyOn(periodoDuracaoService, 'query').mockReturnValue(of(new HttpResponse({ body: periodoDuracaoCollection })));
      const expectedCollection: IPeriodoDuracao[] = [periodoDuracao, ...periodoDuracaoCollection];
      jest.spyOn(periodoDuracaoService, 'addPeriodoDuracaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ turma });
      comp.ngOnInit();

      expect(periodoDuracaoService.query).toHaveBeenCalled();
      expect(periodoDuracaoService.addPeriodoDuracaoToCollectionIfMissing).toHaveBeenCalledWith(periodoDuracaoCollection, periodoDuracao);
      expect(comp.periodoDuracaosCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const turma: ITurma = { id: 456 };
      const periodoDuracao: IPeriodoDuracao = { id: 3090 };
      turma.periodoDuracao = periodoDuracao;

      activatedRoute.data = of({ turma });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(turma));
      expect(comp.periodoDuracaosCollection).toContain(periodoDuracao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Turma>>();
      const turma = { id: 123 };
      jest.spyOn(turmaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ turma });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: turma }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(turmaService.update).toHaveBeenCalledWith(turma);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Turma>>();
      const turma = new Turma();
      jest.spyOn(turmaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ turma });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: turma }));
      saveSubject.complete();

      // THEN
      expect(turmaService.create).toHaveBeenCalledWith(turma);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Turma>>();
      const turma = { id: 123 };
      jest.spyOn(turmaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ turma });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(turmaService.update).toHaveBeenCalledWith(turma);
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
