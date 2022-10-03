import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FotoService } from '../service/foto.service';
import { IFoto, Foto } from '../foto.model';

import { FotoUpdateComponent } from './foto-update.component';

describe('Foto Management Update Component', () => {
  let comp: FotoUpdateComponent;
  let fixture: ComponentFixture<FotoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fotoService: FotoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FotoUpdateComponent],
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
      .overrideTemplate(FotoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FotoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fotoService = TestBed.inject(FotoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const foto: IFoto = { id: 456 };

      activatedRoute.data = of({ foto });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(foto));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Foto>>();
      const foto = { id: 123 };
      jest.spyOn(fotoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ foto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: foto }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(fotoService.update).toHaveBeenCalledWith(foto);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Foto>>();
      const foto = new Foto();
      jest.spyOn(fotoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ foto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: foto }));
      saveSubject.complete();

      // THEN
      expect(fotoService.create).toHaveBeenCalledWith(foto);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Foto>>();
      const foto = { id: 123 };
      jest.spyOn(fotoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ foto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fotoService.update).toHaveBeenCalledWith(foto);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
