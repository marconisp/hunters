import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AgendaColaboradorService } from '../service/agenda-colaborador.service';
import { IAgendaColaborador, AgendaColaborador } from '../agenda-colaborador.model';
import { IPeriodoDuracao } from 'app/entities/config/periodo-duracao/periodo-duracao.model';
import { PeriodoDuracaoService } from 'app/entities/config/periodo-duracao/service/periodo-duracao.service';
import { IColaborador } from 'app/entities/user/colaborador/colaborador.model';
import { ColaboradorService } from 'app/entities/user/colaborador/service/colaborador.service';

import { AgendaColaboradorUpdateComponent } from './agenda-colaborador-update.component';

describe('AgendaColaborador Management Update Component', () => {
  let comp: AgendaColaboradorUpdateComponent;
  let fixture: ComponentFixture<AgendaColaboradorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let agendaColaboradorService: AgendaColaboradorService;
  let periodoDuracaoService: PeriodoDuracaoService;
  let colaboradorService: ColaboradorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AgendaColaboradorUpdateComponent],
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
      .overrideTemplate(AgendaColaboradorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AgendaColaboradorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    agendaColaboradorService = TestBed.inject(AgendaColaboradorService);
    periodoDuracaoService = TestBed.inject(PeriodoDuracaoService);
    colaboradorService = TestBed.inject(ColaboradorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call periodoDuracao query and add missing value', () => {
      const agendaColaborador: IAgendaColaborador = { id: 456 };
      const periodoDuracao: IPeriodoDuracao = { id: 22685 };
      agendaColaborador.periodoDuracao = periodoDuracao;

      const periodoDuracaoCollection: IPeriodoDuracao[] = [{ id: 81807 }];
      jest.spyOn(periodoDuracaoService, 'query').mockReturnValue(of(new HttpResponse({ body: periodoDuracaoCollection })));
      const expectedCollection: IPeriodoDuracao[] = [periodoDuracao, ...periodoDuracaoCollection];
      jest.spyOn(periodoDuracaoService, 'addPeriodoDuracaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ agendaColaborador });
      comp.ngOnInit();

      expect(periodoDuracaoService.query).toHaveBeenCalled();
      expect(periodoDuracaoService.addPeriodoDuracaoToCollectionIfMissing).toHaveBeenCalledWith(periodoDuracaoCollection, periodoDuracao);
      expect(comp.periodoDuracaosCollection).toEqual(expectedCollection);
    });

    it('Should call Colaborador query and add missing value', () => {
      const agendaColaborador: IAgendaColaborador = { id: 456 };
      const colaborador: IColaborador = { id: 20087 };
      agendaColaborador.colaborador = colaborador;

      const colaboradorCollection: IColaborador[] = [{ id: 52665 }];
      jest.spyOn(colaboradorService, 'query').mockReturnValue(of(new HttpResponse({ body: colaboradorCollection })));
      const additionalColaboradors = [colaborador];
      const expectedCollection: IColaborador[] = [...additionalColaboradors, ...colaboradorCollection];
      jest.spyOn(colaboradorService, 'addColaboradorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ agendaColaborador });
      comp.ngOnInit();

      expect(colaboradorService.query).toHaveBeenCalled();
      expect(colaboradorService.addColaboradorToCollectionIfMissing).toHaveBeenCalledWith(colaboradorCollection, ...additionalColaboradors);
      expect(comp.colaboradorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const agendaColaborador: IAgendaColaborador = { id: 456 };
      const periodoDuracao: IPeriodoDuracao = { id: 44647 };
      agendaColaborador.periodoDuracao = periodoDuracao;
      const colaborador: IColaborador = { id: 17962 };
      agendaColaborador.colaborador = colaborador;

      activatedRoute.data = of({ agendaColaborador });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(agendaColaborador));
      expect(comp.periodoDuracaosCollection).toContain(periodoDuracao);
      expect(comp.colaboradorsSharedCollection).toContain(colaborador);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AgendaColaborador>>();
      const agendaColaborador = { id: 123 };
      jest.spyOn(agendaColaboradorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agendaColaborador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: agendaColaborador }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(agendaColaboradorService.update).toHaveBeenCalledWith(agendaColaborador);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AgendaColaborador>>();
      const agendaColaborador = new AgendaColaborador();
      jest.spyOn(agendaColaboradorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agendaColaborador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: agendaColaborador }));
      saveSubject.complete();

      // THEN
      expect(agendaColaboradorService.create).toHaveBeenCalledWith(agendaColaborador);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AgendaColaborador>>();
      const agendaColaborador = { id: 123 };
      jest.spyOn(agendaColaboradorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agendaColaborador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(agendaColaboradorService.update).toHaveBeenCalledWith(agendaColaborador);
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

    describe('trackColaboradorById', () => {
      it('Should return tracked Colaborador primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackColaboradorById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
