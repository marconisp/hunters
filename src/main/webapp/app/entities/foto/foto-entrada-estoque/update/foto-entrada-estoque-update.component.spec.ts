import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FotoEntradaEstoqueService } from '../service/foto-entrada-estoque.service';
import { IFotoEntradaEstoque, FotoEntradaEstoque } from '../foto-entrada-estoque.model';
import { IEntradaEstoque } from 'app/entities/controle/entrada-estoque/entrada-estoque.model';
import { EntradaEstoqueService } from 'app/entities/controle/entrada-estoque/service/entrada-estoque.service';

import { FotoEntradaEstoqueUpdateComponent } from './foto-entrada-estoque-update.component';

describe('FotoEntradaEstoque Management Update Component', () => {
  let comp: FotoEntradaEstoqueUpdateComponent;
  let fixture: ComponentFixture<FotoEntradaEstoqueUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fotoEntradaEstoqueService: FotoEntradaEstoqueService;
  let entradaEstoqueService: EntradaEstoqueService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FotoEntradaEstoqueUpdateComponent],
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
      .overrideTemplate(FotoEntradaEstoqueUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FotoEntradaEstoqueUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fotoEntradaEstoqueService = TestBed.inject(FotoEntradaEstoqueService);
    entradaEstoqueService = TestBed.inject(EntradaEstoqueService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call EntradaEstoque query and add missing value', () => {
      const fotoEntradaEstoque: IFotoEntradaEstoque = { id: 456 };
      const entradaEstoque: IEntradaEstoque = { id: 92244 };
      fotoEntradaEstoque.entradaEstoque = entradaEstoque;

      const entradaEstoqueCollection: IEntradaEstoque[] = [{ id: 84632 }];
      jest.spyOn(entradaEstoqueService, 'query').mockReturnValue(of(new HttpResponse({ body: entradaEstoqueCollection })));
      const additionalEntradaEstoques = [entradaEstoque];
      const expectedCollection: IEntradaEstoque[] = [...additionalEntradaEstoques, ...entradaEstoqueCollection];
      jest.spyOn(entradaEstoqueService, 'addEntradaEstoqueToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fotoEntradaEstoque });
      comp.ngOnInit();

      expect(entradaEstoqueService.query).toHaveBeenCalled();
      expect(entradaEstoqueService.addEntradaEstoqueToCollectionIfMissing).toHaveBeenCalledWith(
        entradaEstoqueCollection,
        ...additionalEntradaEstoques
      );
      expect(comp.entradaEstoquesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const fotoEntradaEstoque: IFotoEntradaEstoque = { id: 456 };
      const entradaEstoque: IEntradaEstoque = { id: 62999 };
      fotoEntradaEstoque.entradaEstoque = entradaEstoque;

      activatedRoute.data = of({ fotoEntradaEstoque });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(fotoEntradaEstoque));
      expect(comp.entradaEstoquesSharedCollection).toContain(entradaEstoque);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FotoEntradaEstoque>>();
      const fotoEntradaEstoque = { id: 123 };
      jest.spyOn(fotoEntradaEstoqueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoEntradaEstoque });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fotoEntradaEstoque }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(fotoEntradaEstoqueService.update).toHaveBeenCalledWith(fotoEntradaEstoque);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FotoEntradaEstoque>>();
      const fotoEntradaEstoque = new FotoEntradaEstoque();
      jest.spyOn(fotoEntradaEstoqueService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoEntradaEstoque });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fotoEntradaEstoque }));
      saveSubject.complete();

      // THEN
      expect(fotoEntradaEstoqueService.create).toHaveBeenCalledWith(fotoEntradaEstoque);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FotoEntradaEstoque>>();
      const fotoEntradaEstoque = { id: 123 };
      jest.spyOn(fotoEntradaEstoqueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoEntradaEstoque });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fotoEntradaEstoqueService.update).toHaveBeenCalledWith(fotoEntradaEstoque);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackEntradaEstoqueById', () => {
      it('Should return tracked EntradaEstoque primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEntradaEstoqueById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
