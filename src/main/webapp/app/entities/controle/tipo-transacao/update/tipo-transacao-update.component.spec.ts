import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TipoTransacaoService } from '../service/tipo-transacao.service';
import { ITipoTransacao, TipoTransacao } from '../tipo-transacao.model';

import { TipoTransacaoUpdateComponent } from './tipo-transacao-update.component';

describe('TipoTransacao Management Update Component', () => {
  let comp: TipoTransacaoUpdateComponent;
  let fixture: ComponentFixture<TipoTransacaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tipoTransacaoService: TipoTransacaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TipoTransacaoUpdateComponent],
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
      .overrideTemplate(TipoTransacaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TipoTransacaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tipoTransacaoService = TestBed.inject(TipoTransacaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const tipoTransacao: ITipoTransacao = { id: 456 };

      activatedRoute.data = of({ tipoTransacao });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(tipoTransacao));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TipoTransacao>>();
      const tipoTransacao = { id: 123 };
      jest.spyOn(tipoTransacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tipoTransacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tipoTransacao }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(tipoTransacaoService.update).toHaveBeenCalledWith(tipoTransacao);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TipoTransacao>>();
      const tipoTransacao = new TipoTransacao();
      jest.spyOn(tipoTransacaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tipoTransacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tipoTransacao }));
      saveSubject.complete();

      // THEN
      expect(tipoTransacaoService.create).toHaveBeenCalledWith(tipoTransacao);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TipoTransacao>>();
      const tipoTransacao = { id: 123 };
      jest.spyOn(tipoTransacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tipoTransacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tipoTransacaoService.update).toHaveBeenCalledWith(tipoTransacao);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
