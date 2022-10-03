import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TipoContratacaoService } from '../service/tipo-contratacao.service';
import { ITipoContratacao, TipoContratacao } from '../tipo-contratacao.model';
import { IColaborador } from 'app/entities/user/colaborador/colaborador.model';
import { ColaboradorService } from 'app/entities/user/colaborador/service/colaborador.service';

import { TipoContratacaoUpdateComponent } from './tipo-contratacao-update.component';

describe('TipoContratacao Management Update Component', () => {
  let comp: TipoContratacaoUpdateComponent;
  let fixture: ComponentFixture<TipoContratacaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tipoContratacaoService: TipoContratacaoService;
  let colaboradorService: ColaboradorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TipoContratacaoUpdateComponent],
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
      .overrideTemplate(TipoContratacaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TipoContratacaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tipoContratacaoService = TestBed.inject(TipoContratacaoService);
    colaboradorService = TestBed.inject(ColaboradorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Colaborador query and add missing value', () => {
      const tipoContratacao: ITipoContratacao = { id: 456 };
      const colaborador: IColaborador = { id: 70973 };
      tipoContratacao.colaborador = colaborador;

      const colaboradorCollection: IColaborador[] = [{ id: 79746 }];
      jest.spyOn(colaboradorService, 'query').mockReturnValue(of(new HttpResponse({ body: colaboradorCollection })));
      const additionalColaboradors = [colaborador];
      const expectedCollection: IColaborador[] = [...additionalColaboradors, ...colaboradorCollection];
      jest.spyOn(colaboradorService, 'addColaboradorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tipoContratacao });
      comp.ngOnInit();

      expect(colaboradorService.query).toHaveBeenCalled();
      expect(colaboradorService.addColaboradorToCollectionIfMissing).toHaveBeenCalledWith(colaboradorCollection, ...additionalColaboradors);
      expect(comp.colaboradorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tipoContratacao: ITipoContratacao = { id: 456 };
      const colaborador: IColaborador = { id: 67164 };
      tipoContratacao.colaborador = colaborador;

      activatedRoute.data = of({ tipoContratacao });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(tipoContratacao));
      expect(comp.colaboradorsSharedCollection).toContain(colaborador);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TipoContratacao>>();
      const tipoContratacao = { id: 123 };
      jest.spyOn(tipoContratacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tipoContratacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tipoContratacao }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(tipoContratacaoService.update).toHaveBeenCalledWith(tipoContratacao);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TipoContratacao>>();
      const tipoContratacao = new TipoContratacao();
      jest.spyOn(tipoContratacaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tipoContratacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tipoContratacao }));
      saveSubject.complete();

      // THEN
      expect(tipoContratacaoService.create).toHaveBeenCalledWith(tipoContratacao);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TipoContratacao>>();
      const tipoContratacao = { id: 123 };
      jest.spyOn(tipoContratacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tipoContratacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tipoContratacaoService.update).toHaveBeenCalledWith(tipoContratacao);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackColaboradorById', () => {
      it('Should return tracked Colaborador primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackColaboradorById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
