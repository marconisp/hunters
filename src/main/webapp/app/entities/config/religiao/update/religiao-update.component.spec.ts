import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ReligiaoService } from '../service/religiao.service';
import { IReligiao, Religiao } from '../religiao.model';

import { ReligiaoUpdateComponent } from './religiao-update.component';

describe('Religiao Management Update Component', () => {
  let comp: ReligiaoUpdateComponent;
  let fixture: ComponentFixture<ReligiaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let religiaoService: ReligiaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ReligiaoUpdateComponent],
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
      .overrideTemplate(ReligiaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReligiaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    religiaoService = TestBed.inject(ReligiaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const religiao: IReligiao = { id: 456 };

      activatedRoute.data = of({ religiao });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(religiao));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Religiao>>();
      const religiao = { id: 123 };
      jest.spyOn(religiaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ religiao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: religiao }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(religiaoService.update).toHaveBeenCalledWith(religiao);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Religiao>>();
      const religiao = new Religiao();
      jest.spyOn(religiaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ religiao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: religiao }));
      saveSubject.complete();

      // THEN
      expect(religiaoService.create).toHaveBeenCalledWith(religiao);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Religiao>>();
      const religiao = { id: 123 };
      jest.spyOn(religiaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ religiao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(religiaoService.update).toHaveBeenCalledWith(religiao);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
