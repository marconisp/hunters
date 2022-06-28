import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TipoPessoaService } from '../service/tipo-pessoa.service';
import { ITipoPessoa, TipoPessoa } from '../tipo-pessoa.model';

import { TipoPessoaUpdateComponent } from './tipo-pessoa-update.component';

describe('TipoPessoa Management Update Component', () => {
  let comp: TipoPessoaUpdateComponent;
  let fixture: ComponentFixture<TipoPessoaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tipoPessoaService: TipoPessoaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TipoPessoaUpdateComponent],
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
      .overrideTemplate(TipoPessoaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TipoPessoaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tipoPessoaService = TestBed.inject(TipoPessoaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const tipoPessoa: ITipoPessoa = { id: 456 };

      activatedRoute.data = of({ tipoPessoa });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(tipoPessoa));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TipoPessoa>>();
      const tipoPessoa = { id: 123 };
      jest.spyOn(tipoPessoaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tipoPessoa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tipoPessoa }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(tipoPessoaService.update).toHaveBeenCalledWith(tipoPessoa);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TipoPessoa>>();
      const tipoPessoa = new TipoPessoa();
      jest.spyOn(tipoPessoaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tipoPessoa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tipoPessoa }));
      saveSubject.complete();

      // THEN
      expect(tipoPessoaService.create).toHaveBeenCalledWith(tipoPessoa);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TipoPessoa>>();
      const tipoPessoa = { id: 123 };
      jest.spyOn(tipoPessoaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tipoPessoa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tipoPessoaService.update).toHaveBeenCalledWith(tipoPessoa);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
