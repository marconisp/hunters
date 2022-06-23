import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RacaService } from '../service/raca.service';
import { IRaca, Raca } from '../raca.model';

import { RacaUpdateComponent } from './raca-update.component';

describe('Raca Management Update Component', () => {
  let comp: RacaUpdateComponent;
  let fixture: ComponentFixture<RacaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let racaService: RacaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RacaUpdateComponent],
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
      .overrideTemplate(RacaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RacaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    racaService = TestBed.inject(RacaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const raca: IRaca = { id: 456 };

      activatedRoute.data = of({ raca });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(raca));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Raca>>();
      const raca = { id: 123 };
      jest.spyOn(racaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ raca });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: raca }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(racaService.update).toHaveBeenCalledWith(raca);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Raca>>();
      const raca = new Raca();
      jest.spyOn(racaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ raca });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: raca }));
      saveSubject.complete();

      // THEN
      expect(racaService.create).toHaveBeenCalledWith(raca);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Raca>>();
      const raca = { id: 123 };
      jest.spyOn(racaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ raca });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(racaService.update).toHaveBeenCalledWith(raca);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
