import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EnderecoEventoService } from '../service/endereco-evento.service';
import { IEnderecoEvento, EnderecoEvento } from '../endereco-evento.model';
import { IEvento } from 'app/entities/evento/evento/evento.model';
import { EventoService } from 'app/entities/evento/evento/service/evento.service';

import { EnderecoEventoUpdateComponent } from './endereco-evento-update.component';

describe('EnderecoEvento Management Update Component', () => {
  let comp: EnderecoEventoUpdateComponent;
  let fixture: ComponentFixture<EnderecoEventoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let enderecoEventoService: EnderecoEventoService;
  let eventoService: EventoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EnderecoEventoUpdateComponent],
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
      .overrideTemplate(EnderecoEventoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EnderecoEventoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    enderecoEventoService = TestBed.inject(EnderecoEventoService);
    eventoService = TestBed.inject(EventoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Evento query and add missing value', () => {
      const enderecoEvento: IEnderecoEvento = { id: 456 };
      const evento: IEvento = { id: 24083 };
      enderecoEvento.evento = evento;

      const eventoCollection: IEvento[] = [{ id: 13490 }];
      jest.spyOn(eventoService, 'query').mockReturnValue(of(new HttpResponse({ body: eventoCollection })));
      const additionalEventos = [evento];
      const expectedCollection: IEvento[] = [...additionalEventos, ...eventoCollection];
      jest.spyOn(eventoService, 'addEventoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ enderecoEvento });
      comp.ngOnInit();

      expect(eventoService.query).toHaveBeenCalled();
      expect(eventoService.addEventoToCollectionIfMissing).toHaveBeenCalledWith(eventoCollection, ...additionalEventos);
      expect(comp.eventosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const enderecoEvento: IEnderecoEvento = { id: 456 };
      const evento: IEvento = { id: 32787 };
      enderecoEvento.evento = evento;

      activatedRoute.data = of({ enderecoEvento });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(enderecoEvento));
      expect(comp.eventosSharedCollection).toContain(evento);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EnderecoEvento>>();
      const enderecoEvento = { id: 123 };
      jest.spyOn(enderecoEventoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enderecoEvento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: enderecoEvento }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(enderecoEventoService.update).toHaveBeenCalledWith(enderecoEvento);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EnderecoEvento>>();
      const enderecoEvento = new EnderecoEvento();
      jest.spyOn(enderecoEventoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enderecoEvento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: enderecoEvento }));
      saveSubject.complete();

      // THEN
      expect(enderecoEventoService.create).toHaveBeenCalledWith(enderecoEvento);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EnderecoEvento>>();
      const enderecoEvento = { id: 123 };
      jest.spyOn(enderecoEventoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enderecoEvento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(enderecoEventoService.update).toHaveBeenCalledWith(enderecoEvento);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackEventoById', () => {
      it('Should return tracked Evento primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEventoById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
