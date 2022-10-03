import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FotoPagarService } from '../service/foto-pagar.service';
import { IFotoPagar, FotoPagar } from '../foto-pagar.model';
import { IPagar } from 'app/entities/controle/pagar/pagar.model';
import { PagarService } from 'app/entities/controle/pagar/service/pagar.service';

import { FotoPagarUpdateComponent } from './foto-pagar-update.component';

describe('FotoPagar Management Update Component', () => {
  let comp: FotoPagarUpdateComponent;
  let fixture: ComponentFixture<FotoPagarUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fotoPagarService: FotoPagarService;
  let pagarService: PagarService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FotoPagarUpdateComponent],
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
      .overrideTemplate(FotoPagarUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FotoPagarUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fotoPagarService = TestBed.inject(FotoPagarService);
    pagarService = TestBed.inject(PagarService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pagar query and add missing value', () => {
      const fotoPagar: IFotoPagar = { id: 456 };
      const pagar: IPagar = { id: 74955 };
      fotoPagar.pagar = pagar;

      const pagarCollection: IPagar[] = [{ id: 48542 }];
      jest.spyOn(pagarService, 'query').mockReturnValue(of(new HttpResponse({ body: pagarCollection })));
      const additionalPagars = [pagar];
      const expectedCollection: IPagar[] = [...additionalPagars, ...pagarCollection];
      jest.spyOn(pagarService, 'addPagarToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fotoPagar });
      comp.ngOnInit();

      expect(pagarService.query).toHaveBeenCalled();
      expect(pagarService.addPagarToCollectionIfMissing).toHaveBeenCalledWith(pagarCollection, ...additionalPagars);
      expect(comp.pagarsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const fotoPagar: IFotoPagar = { id: 456 };
      const pagar: IPagar = { id: 80116 };
      fotoPagar.pagar = pagar;

      activatedRoute.data = of({ fotoPagar });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(fotoPagar));
      expect(comp.pagarsSharedCollection).toContain(pagar);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FotoPagar>>();
      const fotoPagar = { id: 123 };
      jest.spyOn(fotoPagarService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoPagar });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fotoPagar }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(fotoPagarService.update).toHaveBeenCalledWith(fotoPagar);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FotoPagar>>();
      const fotoPagar = new FotoPagar();
      jest.spyOn(fotoPagarService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoPagar });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fotoPagar }));
      saveSubject.complete();

      // THEN
      expect(fotoPagarService.create).toHaveBeenCalledWith(fotoPagar);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FotoPagar>>();
      const fotoPagar = { id: 123 };
      jest.spyOn(fotoPagarService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoPagar });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fotoPagarService.update).toHaveBeenCalledWith(fotoPagar);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPagarById', () => {
      it('Should return tracked Pagar primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPagarById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
