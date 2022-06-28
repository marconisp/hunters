import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EstadoCivilService } from '../service/estado-civil.service';
import { IEstadoCivil, EstadoCivil } from '../estado-civil.model';

import { EstadoCivilUpdateComponent } from './estado-civil-update.component';

describe('EstadoCivil Management Update Component', () => {
  let comp: EstadoCivilUpdateComponent;
  let fixture: ComponentFixture<EstadoCivilUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let estadoCivilService: EstadoCivilService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EstadoCivilUpdateComponent],
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
      .overrideTemplate(EstadoCivilUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EstadoCivilUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    estadoCivilService = TestBed.inject(EstadoCivilService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const estadoCivil: IEstadoCivil = { id: 456 };

      activatedRoute.data = of({ estadoCivil });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(estadoCivil));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EstadoCivil>>();
      const estadoCivil = { id: 123 };
      jest.spyOn(estadoCivilService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ estadoCivil });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: estadoCivil }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(estadoCivilService.update).toHaveBeenCalledWith(estadoCivil);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EstadoCivil>>();
      const estadoCivil = new EstadoCivil();
      jest.spyOn(estadoCivilService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ estadoCivil });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: estadoCivil }));
      saveSubject.complete();

      // THEN
      expect(estadoCivilService.create).toHaveBeenCalledWith(estadoCivil);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EstadoCivil>>();
      const estadoCivil = { id: 123 };
      jest.spyOn(estadoCivilService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ estadoCivil });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(estadoCivilService.update).toHaveBeenCalledWith(estadoCivil);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
