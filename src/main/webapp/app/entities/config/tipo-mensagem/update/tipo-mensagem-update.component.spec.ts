import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TipoMensagemService } from '../service/tipo-mensagem.service';
import { ITipoMensagem, TipoMensagem } from '../tipo-mensagem.model';

import { TipoMensagemUpdateComponent } from './tipo-mensagem-update.component';

describe('TipoMensagem Management Update Component', () => {
  let comp: TipoMensagemUpdateComponent;
  let fixture: ComponentFixture<TipoMensagemUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tipoMensagemService: TipoMensagemService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TipoMensagemUpdateComponent],
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
      .overrideTemplate(TipoMensagemUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TipoMensagemUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tipoMensagemService = TestBed.inject(TipoMensagemService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const tipoMensagem: ITipoMensagem = { id: 456 };

      activatedRoute.data = of({ tipoMensagem });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(tipoMensagem));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TipoMensagem>>();
      const tipoMensagem = { id: 123 };
      jest.spyOn(tipoMensagemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tipoMensagem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tipoMensagem }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(tipoMensagemService.update).toHaveBeenCalledWith(tipoMensagem);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TipoMensagem>>();
      const tipoMensagem = new TipoMensagem();
      jest.spyOn(tipoMensagemService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tipoMensagem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tipoMensagem }));
      saveSubject.complete();

      // THEN
      expect(tipoMensagemService.create).toHaveBeenCalledWith(tipoMensagem);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TipoMensagem>>();
      const tipoMensagem = { id: 123 };
      jest.spyOn(tipoMensagemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tipoMensagem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tipoMensagemService.update).toHaveBeenCalledWith(tipoMensagem);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
