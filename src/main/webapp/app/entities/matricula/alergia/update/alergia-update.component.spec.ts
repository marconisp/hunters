import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AlergiaService } from '../service/alergia.service';
import { IAlergia, Alergia } from '../alergia.model';

import { AlergiaUpdateComponent } from './alergia-update.component';

describe('Alergia Management Update Component', () => {
  let comp: AlergiaUpdateComponent;
  let fixture: ComponentFixture<AlergiaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let alergiaService: AlergiaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AlergiaUpdateComponent],
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
      .overrideTemplate(AlergiaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AlergiaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    alergiaService = TestBed.inject(AlergiaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const alergia: IAlergia = { id: 456 };

      activatedRoute.data = of({ alergia });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(alergia));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Alergia>>();
      const alergia = { id: 123 };
      jest.spyOn(alergiaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ alergia });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: alergia }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(alergiaService.update).toHaveBeenCalledWith(alergia);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Alergia>>();
      const alergia = new Alergia();
      jest.spyOn(alergiaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ alergia });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: alergia }));
      saveSubject.complete();

      // THEN
      expect(alergiaService.create).toHaveBeenCalledWith(alergia);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Alergia>>();
      const alergia = { id: 123 };
      jest.spyOn(alergiaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ alergia });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(alergiaService.update).toHaveBeenCalledWith(alergia);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
