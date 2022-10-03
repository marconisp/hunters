import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ReceberDeService } from '../service/receber-de.service';
import { IReceberDe, ReceberDe } from '../receber-de.model';

import { ReceberDeUpdateComponent } from './receber-de-update.component';

describe('ReceberDe Management Update Component', () => {
  let comp: ReceberDeUpdateComponent;
  let fixture: ComponentFixture<ReceberDeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let receberDeService: ReceberDeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ReceberDeUpdateComponent],
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
      .overrideTemplate(ReceberDeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReceberDeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    receberDeService = TestBed.inject(ReceberDeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const receberDe: IReceberDe = { id: 456 };

      activatedRoute.data = of({ receberDe });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(receberDe));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ReceberDe>>();
      const receberDe = { id: 123 };
      jest.spyOn(receberDeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ receberDe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: receberDe }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(receberDeService.update).toHaveBeenCalledWith(receberDe);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ReceberDe>>();
      const receberDe = new ReceberDe();
      jest.spyOn(receberDeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ receberDe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: receberDe }));
      saveSubject.complete();

      // THEN
      expect(receberDeService.create).toHaveBeenCalledWith(receberDe);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ReceberDe>>();
      const receberDe = { id: 123 };
      jest.spyOn(receberDeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ receberDe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(receberDeService.update).toHaveBeenCalledWith(receberDe);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
