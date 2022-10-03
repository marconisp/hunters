import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SubGrupoProdutoService } from '../service/sub-grupo-produto.service';
import { ISubGrupoProduto, SubGrupoProduto } from '../sub-grupo-produto.model';
import { IGrupoProduto } from 'app/entities/controle/grupo-produto/grupo-produto.model';
import { GrupoProdutoService } from 'app/entities/controle/grupo-produto/service/grupo-produto.service';

import { SubGrupoProdutoUpdateComponent } from './sub-grupo-produto-update.component';

describe('SubGrupoProduto Management Update Component', () => {
  let comp: SubGrupoProdutoUpdateComponent;
  let fixture: ComponentFixture<SubGrupoProdutoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let subGrupoProdutoService: SubGrupoProdutoService;
  let grupoProdutoService: GrupoProdutoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SubGrupoProdutoUpdateComponent],
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
      .overrideTemplate(SubGrupoProdutoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SubGrupoProdutoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    subGrupoProdutoService = TestBed.inject(SubGrupoProdutoService);
    grupoProdutoService = TestBed.inject(GrupoProdutoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call GrupoProduto query and add missing value', () => {
      const subGrupoProduto: ISubGrupoProduto = { id: 456 };
      const grupoProduto: IGrupoProduto = { id: 50204 };
      subGrupoProduto.grupoProduto = grupoProduto;

      const grupoProdutoCollection: IGrupoProduto[] = [{ id: 86803 }];
      jest.spyOn(grupoProdutoService, 'query').mockReturnValue(of(new HttpResponse({ body: grupoProdutoCollection })));
      const additionalGrupoProdutos = [grupoProduto];
      const expectedCollection: IGrupoProduto[] = [...additionalGrupoProdutos, ...grupoProdutoCollection];
      jest.spyOn(grupoProdutoService, 'addGrupoProdutoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ subGrupoProduto });
      comp.ngOnInit();

      expect(grupoProdutoService.query).toHaveBeenCalled();
      expect(grupoProdutoService.addGrupoProdutoToCollectionIfMissing).toHaveBeenCalledWith(
        grupoProdutoCollection,
        ...additionalGrupoProdutos
      );
      expect(comp.grupoProdutosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const subGrupoProduto: ISubGrupoProduto = { id: 456 };
      const grupoProduto: IGrupoProduto = { id: 78707 };
      subGrupoProduto.grupoProduto = grupoProduto;

      activatedRoute.data = of({ subGrupoProduto });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(subGrupoProduto));
      expect(comp.grupoProdutosSharedCollection).toContain(grupoProduto);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SubGrupoProduto>>();
      const subGrupoProduto = { id: 123 };
      jest.spyOn(subGrupoProdutoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subGrupoProduto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subGrupoProduto }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(subGrupoProdutoService.update).toHaveBeenCalledWith(subGrupoProduto);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SubGrupoProduto>>();
      const subGrupoProduto = new SubGrupoProduto();
      jest.spyOn(subGrupoProdutoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subGrupoProduto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subGrupoProduto }));
      saveSubject.complete();

      // THEN
      expect(subGrupoProdutoService.create).toHaveBeenCalledWith(subGrupoProduto);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SubGrupoProduto>>();
      const subGrupoProduto = { id: 123 };
      jest.spyOn(subGrupoProdutoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subGrupoProduto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(subGrupoProdutoService.update).toHaveBeenCalledWith(subGrupoProduto);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackGrupoProdutoById', () => {
      it('Should return tracked GrupoProduto primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackGrupoProdutoById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
