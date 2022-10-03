import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TipoReceberService } from '../service/tipo-receber.service';
import { ITipoReceber, TipoReceber } from '../tipo-receber.model';

import { TipoReceberUpdateComponent } from './tipo-receber-update.component';

describe('TipoReceber Management Update Component', () => {
  let comp: TipoReceberUpdateComponent;
  let fixture: ComponentFixture<TipoReceberUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tipoReceberService: TipoReceberService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TipoReceberUpdateComponent],
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
      .overrideTemplate(TipoReceberUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TipoReceberUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tipoReceberService = TestBed.inject(TipoReceberService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const tipoReceber: ITipoReceber = { id: 456 };

      activatedRoute.data = of({ tipoReceber });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(tipoReceber));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TipoReceber>>();
      const tipoReceber = { id: 123 };
      jest.spyOn(tipoReceberService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tipoReceber });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tipoReceber }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(tipoReceberService.update).toHaveBeenCalledWith(tipoReceber);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TipoReceber>>();
      const tipoReceber = new TipoReceber();
      jest.spyOn(tipoReceberService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tipoReceber });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tipoReceber }));
      saveSubject.complete();

      // THEN
      expect(tipoReceberService.create).toHaveBeenCalledWith(tipoReceber);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TipoReceber>>();
      const tipoReceber = { id: 123 };
      jest.spyOn(tipoReceberService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tipoReceber });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tipoReceberService.update).toHaveBeenCalledWith(tipoReceber);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
