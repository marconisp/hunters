import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FotoSaidaEstoqueService } from '../service/foto-saida-estoque.service';
import { IFotoSaidaEstoque, FotoSaidaEstoque } from '../foto-saida-estoque.model';
import { ISaidaEstoque } from 'app/entities/controle/saida-estoque/saida-estoque.model';
import { SaidaEstoqueService } from 'app/entities/controle/saida-estoque/service/saida-estoque.service';

import { FotoSaidaEstoqueUpdateComponent } from './foto-saida-estoque-update.component';

describe('FotoSaidaEstoque Management Update Component', () => {
  let comp: FotoSaidaEstoqueUpdateComponent;
  let fixture: ComponentFixture<FotoSaidaEstoqueUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fotoSaidaEstoqueService: FotoSaidaEstoqueService;
  let saidaEstoqueService: SaidaEstoqueService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FotoSaidaEstoqueUpdateComponent],
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
      .overrideTemplate(FotoSaidaEstoqueUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FotoSaidaEstoqueUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fotoSaidaEstoqueService = TestBed.inject(FotoSaidaEstoqueService);
    saidaEstoqueService = TestBed.inject(SaidaEstoqueService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SaidaEstoque query and add missing value', () => {
      const fotoSaidaEstoque: IFotoSaidaEstoque = { id: 456 };
      const saidaEstoque: ISaidaEstoque = { id: 79677 };
      fotoSaidaEstoque.saidaEstoque = saidaEstoque;

      const saidaEstoqueCollection: ISaidaEstoque[] = [{ id: 58897 }];
      jest.spyOn(saidaEstoqueService, 'query').mockReturnValue(of(new HttpResponse({ body: saidaEstoqueCollection })));
      const additionalSaidaEstoques = [saidaEstoque];
      const expectedCollection: ISaidaEstoque[] = [...additionalSaidaEstoques, ...saidaEstoqueCollection];
      jest.spyOn(saidaEstoqueService, 'addSaidaEstoqueToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fotoSaidaEstoque });
      comp.ngOnInit();

      expect(saidaEstoqueService.query).toHaveBeenCalled();
      expect(saidaEstoqueService.addSaidaEstoqueToCollectionIfMissing).toHaveBeenCalledWith(
        saidaEstoqueCollection,
        ...additionalSaidaEstoques
      );
      expect(comp.saidaEstoquesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const fotoSaidaEstoque: IFotoSaidaEstoque = { id: 456 };
      const saidaEstoque: ISaidaEstoque = { id: 78206 };
      fotoSaidaEstoque.saidaEstoque = saidaEstoque;

      activatedRoute.data = of({ fotoSaidaEstoque });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(fotoSaidaEstoque));
      expect(comp.saidaEstoquesSharedCollection).toContain(saidaEstoque);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FotoSaidaEstoque>>();
      const fotoSaidaEstoque = { id: 123 };
      jest.spyOn(fotoSaidaEstoqueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoSaidaEstoque });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fotoSaidaEstoque }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(fotoSaidaEstoqueService.update).toHaveBeenCalledWith(fotoSaidaEstoque);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FotoSaidaEstoque>>();
      const fotoSaidaEstoque = new FotoSaidaEstoque();
      jest.spyOn(fotoSaidaEstoqueService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoSaidaEstoque });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fotoSaidaEstoque }));
      saveSubject.complete();

      // THEN
      expect(fotoSaidaEstoqueService.create).toHaveBeenCalledWith(fotoSaidaEstoque);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FotoSaidaEstoque>>();
      const fotoSaidaEstoque = { id: 123 };
      jest.spyOn(fotoSaidaEstoqueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoSaidaEstoque });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fotoSaidaEstoqueService.update).toHaveBeenCalledWith(fotoSaidaEstoque);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSaidaEstoqueById', () => {
      it('Should return tracked SaidaEstoque primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSaidaEstoqueById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
