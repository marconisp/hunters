import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PagarParaService } from '../service/pagar-para.service';
import { IPagarPara, PagarPara } from '../pagar-para.model';

import { PagarParaUpdateComponent } from './pagar-para-update.component';

describe('PagarPara Management Update Component', () => {
  let comp: PagarParaUpdateComponent;
  let fixture: ComponentFixture<PagarParaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pagarParaService: PagarParaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PagarParaUpdateComponent],
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
      .overrideTemplate(PagarParaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PagarParaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pagarParaService = TestBed.inject(PagarParaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const pagarPara: IPagarPara = { id: 456 };

      activatedRoute.data = of({ pagarPara });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(pagarPara));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PagarPara>>();
      const pagarPara = { id: 123 };
      jest.spyOn(pagarParaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pagarPara });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pagarPara }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(pagarParaService.update).toHaveBeenCalledWith(pagarPara);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PagarPara>>();
      const pagarPara = new PagarPara();
      jest.spyOn(pagarParaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pagarPara });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pagarPara }));
      saveSubject.complete();

      // THEN
      expect(pagarParaService.create).toHaveBeenCalledWith(pagarPara);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PagarPara>>();
      const pagarPara = { id: 123 };
      jest.spyOn(pagarParaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pagarPara });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pagarParaService.update).toHaveBeenCalledWith(pagarPara);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
