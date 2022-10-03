import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MateriaService } from '../service/materia.service';
import { IMateria, Materia } from '../materia.model';

import { MateriaUpdateComponent } from './materia-update.component';

describe('Materia Management Update Component', () => {
  let comp: MateriaUpdateComponent;
  let fixture: ComponentFixture<MateriaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let materiaService: MateriaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MateriaUpdateComponent],
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
      .overrideTemplate(MateriaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MateriaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    materiaService = TestBed.inject(MateriaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const materia: IMateria = { id: 456 };

      activatedRoute.data = of({ materia });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(materia));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Materia>>();
      const materia = { id: 123 };
      jest.spyOn(materiaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ materia });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: materia }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(materiaService.update).toHaveBeenCalledWith(materia);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Materia>>();
      const materia = new Materia();
      jest.spyOn(materiaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ materia });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: materia }));
      saveSubject.complete();

      // THEN
      expect(materiaService.create).toHaveBeenCalledWith(materia);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Materia>>();
      const materia = { id: 123 };
      jest.spyOn(materiaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ materia });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(materiaService.update).toHaveBeenCalledWith(materia);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
