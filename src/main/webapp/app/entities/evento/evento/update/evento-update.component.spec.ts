import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EventoService } from '../service/evento.service';
import { IEvento, Evento } from '../evento.model';
import { IPeriodoDuracao } from 'app/entities/config/periodo-duracao/periodo-duracao.model';
import { PeriodoDuracaoService } from 'app/entities/config/periodo-duracao/service/periodo-duracao.service';
import { IDadosPessoais1 } from 'app/entities/user/dados-pessoais-1/dados-pessoais-1.model';
import { DadosPessoais1Service } from 'app/entities/user/dados-pessoais-1/service/dados-pessoais-1.service';

import { EventoUpdateComponent } from './evento-update.component';

describe('Evento Management Update Component', () => {
  let comp: EventoUpdateComponent;
  let fixture: ComponentFixture<EventoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let eventoService: EventoService;
  let periodoDuracaoService: PeriodoDuracaoService;
  let dadosPessoais1Service: DadosPessoais1Service;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EventoUpdateComponent],
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
      .overrideTemplate(EventoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EventoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    eventoService = TestBed.inject(EventoService);
    periodoDuracaoService = TestBed.inject(PeriodoDuracaoService);
    dadosPessoais1Service = TestBed.inject(DadosPessoais1Service);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call periodoDuracao query and add missing value', () => {
      const evento: IEvento = { id: 456 };
      const periodoDuracao: IPeriodoDuracao = { id: 897 };
      evento.periodoDuracao = periodoDuracao;

      const periodoDuracaoCollection: IPeriodoDuracao[] = [{ id: 93498 }];
      jest.spyOn(periodoDuracaoService, 'query').mockReturnValue(of(new HttpResponse({ body: periodoDuracaoCollection })));
      const expectedCollection: IPeriodoDuracao[] = [periodoDuracao, ...periodoDuracaoCollection];
      jest.spyOn(periodoDuracaoService, 'addPeriodoDuracaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ evento });
      comp.ngOnInit();

      expect(periodoDuracaoService.query).toHaveBeenCalled();
      expect(periodoDuracaoService.addPeriodoDuracaoToCollectionIfMissing).toHaveBeenCalledWith(periodoDuracaoCollection, periodoDuracao);
      expect(comp.periodoDuracaosCollection).toEqual(expectedCollection);
    });

    it('Should call DadosPessoais1 query and add missing value', () => {
      const evento: IEvento = { id: 456 };
      const dadosPessoais1: IDadosPessoais1 = { id: 41843 };
      evento.dadosPessoais1 = dadosPessoais1;

      const dadosPessoais1Collection: IDadosPessoais1[] = [{ id: 65497 }];
      jest.spyOn(dadosPessoais1Service, 'query').mockReturnValue(of(new HttpResponse({ body: dadosPessoais1Collection })));
      const additionalDadosPessoais1s = [dadosPessoais1];
      const expectedCollection: IDadosPessoais1[] = [...additionalDadosPessoais1s, ...dadosPessoais1Collection];
      jest.spyOn(dadosPessoais1Service, 'addDadosPessoais1ToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ evento });
      comp.ngOnInit();

      expect(dadosPessoais1Service.query).toHaveBeenCalled();
      expect(dadosPessoais1Service.addDadosPessoais1ToCollectionIfMissing).toHaveBeenCalledWith(
        dadosPessoais1Collection,
        ...additionalDadosPessoais1s
      );
      expect(comp.dadosPessoais1sSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const evento: IEvento = { id: 456 };
      const periodoDuracao: IPeriodoDuracao = { id: 60590 };
      evento.periodoDuracao = periodoDuracao;
      const dadosPessoais1: IDadosPessoais1 = { id: 63583 };
      evento.dadosPessoais1 = dadosPessoais1;

      activatedRoute.data = of({ evento });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(evento));
      expect(comp.periodoDuracaosCollection).toContain(periodoDuracao);
      expect(comp.dadosPessoais1sSharedCollection).toContain(dadosPessoais1);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Evento>>();
      const evento = { id: 123 };
      jest.spyOn(eventoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ evento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: evento }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(eventoService.update).toHaveBeenCalledWith(evento);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Evento>>();
      const evento = new Evento();
      jest.spyOn(eventoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ evento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: evento }));
      saveSubject.complete();

      // THEN
      expect(eventoService.create).toHaveBeenCalledWith(evento);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Evento>>();
      const evento = { id: 123 };
      jest.spyOn(eventoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ evento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(eventoService.update).toHaveBeenCalledWith(evento);
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

    describe('trackDadosPessoais1ById', () => {
      it('Should return tracked DadosPessoais1 primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDadosPessoais1ById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
