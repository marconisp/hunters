import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FotoReceberService } from '../service/foto-receber.service';
import { IFotoReceber, FotoReceber } from '../foto-receber.model';
import { IReceber } from 'app/entities/controle/receber/receber.model';
import { ReceberService } from 'app/entities/controle/receber/service/receber.service';

import { FotoReceberUpdateComponent } from './foto-receber-update.component';

describe('FotoReceber Management Update Component', () => {
  let comp: FotoReceberUpdateComponent;
  let fixture: ComponentFixture<FotoReceberUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fotoReceberService: FotoReceberService;
  let receberService: ReceberService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FotoReceberUpdateComponent],
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
      .overrideTemplate(FotoReceberUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FotoReceberUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fotoReceberService = TestBed.inject(FotoReceberService);
    receberService = TestBed.inject(ReceberService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Receber query and add missing value', () => {
      const fotoReceber: IFotoReceber = { id: 456 };
      const receber: IReceber = { id: 99668 };
      fotoReceber.receber = receber;

      const receberCollection: IReceber[] = [{ id: 42849 }];
      jest.spyOn(receberService, 'query').mockReturnValue(of(new HttpResponse({ body: receberCollection })));
      const additionalRecebers = [receber];
      const expectedCollection: IReceber[] = [...additionalRecebers, ...receberCollection];
      jest.spyOn(receberService, 'addReceberToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fotoReceber });
      comp.ngOnInit();

      expect(receberService.query).toHaveBeenCalled();
      expect(receberService.addReceberToCollectionIfMissing).toHaveBeenCalledWith(receberCollection, ...additionalRecebers);
      expect(comp.recebersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const fotoReceber: IFotoReceber = { id: 456 };
      const receber: IReceber = { id: 72981 };
      fotoReceber.receber = receber;

      activatedRoute.data = of({ fotoReceber });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(fotoReceber));
      expect(comp.recebersSharedCollection).toContain(receber);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FotoReceber>>();
      const fotoReceber = { id: 123 };
      jest.spyOn(fotoReceberService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoReceber });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fotoReceber }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(fotoReceberService.update).toHaveBeenCalledWith(fotoReceber);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FotoReceber>>();
      const fotoReceber = new FotoReceber();
      jest.spyOn(fotoReceberService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoReceber });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fotoReceber }));
      saveSubject.complete();

      // THEN
      expect(fotoReceberService.create).toHaveBeenCalledWith(fotoReceber);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FotoReceber>>();
      const fotoReceber = { id: 123 };
      jest.spyOn(fotoReceberService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoReceber });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fotoReceberService.update).toHaveBeenCalledWith(fotoReceber);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackReceberById', () => {
      it('Should return tracked Receber primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackReceberById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
