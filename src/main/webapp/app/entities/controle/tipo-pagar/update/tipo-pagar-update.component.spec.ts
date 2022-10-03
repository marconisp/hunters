import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TipoPagarService } from '../service/tipo-pagar.service';
import { ITipoPagar, TipoPagar } from '../tipo-pagar.model';

import { TipoPagarUpdateComponent } from './tipo-pagar-update.component';

describe('TipoPagar Management Update Component', () => {
  let comp: TipoPagarUpdateComponent;
  let fixture: ComponentFixture<TipoPagarUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tipoPagarService: TipoPagarService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TipoPagarUpdateComponent],
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
      .overrideTemplate(TipoPagarUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TipoPagarUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tipoPagarService = TestBed.inject(TipoPagarService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const tipoPagar: ITipoPagar = { id: 456 };

      activatedRoute.data = of({ tipoPagar });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(tipoPagar));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TipoPagar>>();
      const tipoPagar = { id: 123 };
      jest.spyOn(tipoPagarService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tipoPagar });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tipoPagar }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(tipoPagarService.update).toHaveBeenCalledWith(tipoPagar);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TipoPagar>>();
      const tipoPagar = new TipoPagar();
      jest.spyOn(tipoPagarService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tipoPagar });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tipoPagar }));
      saveSubject.complete();

      // THEN
      expect(tipoPagarService.create).toHaveBeenCalledWith(tipoPagar);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TipoPagar>>();
      const tipoPagar = { id: 123 };
      jest.spyOn(tipoPagarService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tipoPagar });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tipoPagarService.update).toHaveBeenCalledWith(tipoPagar);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
