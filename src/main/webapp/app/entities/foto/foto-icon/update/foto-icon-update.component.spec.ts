import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FotoIconService } from '../service/foto-icon.service';
import { IFotoIcon, FotoIcon } from '../foto-icon.model';

import { FotoIconUpdateComponent } from './foto-icon-update.component';

describe('FotoIcon Management Update Component', () => {
  let comp: FotoIconUpdateComponent;
  let fixture: ComponentFixture<FotoIconUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fotoIconService: FotoIconService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FotoIconUpdateComponent],
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
      .overrideTemplate(FotoIconUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FotoIconUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fotoIconService = TestBed.inject(FotoIconService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const fotoIcon: IFotoIcon = { id: 456 };

      activatedRoute.data = of({ fotoIcon });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(fotoIcon));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FotoIcon>>();
      const fotoIcon = { id: 123 };
      jest.spyOn(fotoIconService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoIcon });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fotoIcon }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(fotoIconService.update).toHaveBeenCalledWith(fotoIcon);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FotoIcon>>();
      const fotoIcon = new FotoIcon();
      jest.spyOn(fotoIconService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoIcon });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fotoIcon }));
      saveSubject.complete();

      // THEN
      expect(fotoIconService.create).toHaveBeenCalledWith(fotoIcon);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FotoIcon>>();
      const fotoIcon = { id: 123 };
      jest.spyOn(fotoIconService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoIcon });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fotoIconService.update).toHaveBeenCalledWith(fotoIcon);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
