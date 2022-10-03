import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EstoqueService } from '../service/estoque.service';
import { IEstoque, Estoque } from '../estoque.model';
import { IProduto } from 'app/entities/controle/produto/produto.model';
import { ProdutoService } from 'app/entities/controle/produto/service/produto.service';
import { IGrupoProduto } from 'app/entities/controle/grupo-produto/grupo-produto.model';
import { GrupoProdutoService } from 'app/entities/controle/grupo-produto/service/grupo-produto.service';
import { ISubGrupoProduto } from 'app/entities/controle/sub-grupo-produto/sub-grupo-produto.model';
import { SubGrupoProdutoService } from 'app/entities/controle/sub-grupo-produto/service/sub-grupo-produto.service';

import { EstoqueUpdateComponent } from './estoque-update.component';

describe('Estoque Management Update Component', () => {
  let comp: EstoqueUpdateComponent;
  let fixture: ComponentFixture<EstoqueUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let estoqueService: EstoqueService;
  let produtoService: ProdutoService;
  let grupoProdutoService: GrupoProdutoService;
  let subGrupoProdutoService: SubGrupoProdutoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EstoqueUpdateComponent],
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
      .overrideTemplate(EstoqueUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EstoqueUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    estoqueService = TestBed.inject(EstoqueService);
    produtoService = TestBed.inject(ProdutoService);
    grupoProdutoService = TestBed.inject(GrupoProdutoService);
    subGrupoProdutoService = TestBed.inject(SubGrupoProdutoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call produto query and add missing value', () => {
      const estoque: IEstoque = { id: 456 };
      const produto: IProduto = { id: 12656 };
      estoque.produto = produto;

      const produtoCollection: IProduto[] = [{ id: 89498 }];
      jest.spyOn(produtoService, 'query').mockReturnValue(of(new HttpResponse({ body: produtoCollection })));
      const expectedCollection: IProduto[] = [produto, ...produtoCollection];
      jest.spyOn(produtoService, 'addProdutoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ estoque });
      comp.ngOnInit();

      expect(produtoService.query).toHaveBeenCalled();
      expect(produtoService.addProdutoToCollectionIfMissing).toHaveBeenCalledWith(produtoCollection, produto);
      expect(comp.produtosCollection).toEqual(expectedCollection);
    });

    it('Should call grupoProduto query and add missing value', () => {
      const estoque: IEstoque = { id: 456 };
      const grupoProduto: IGrupoProduto = { id: 50168 };
      estoque.grupoProduto = grupoProduto;

      const grupoProdutoCollection: IGrupoProduto[] = [{ id: 5849 }];
      jest.spyOn(grupoProdutoService, 'query').mockReturnValue(of(new HttpResponse({ body: grupoProdutoCollection })));
      const expectedCollection: IGrupoProduto[] = [grupoProduto, ...grupoProdutoCollection];
      jest.spyOn(grupoProdutoService, 'addGrupoProdutoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ estoque });
      comp.ngOnInit();

      expect(grupoProdutoService.query).toHaveBeenCalled();
      expect(grupoProdutoService.addGrupoProdutoToCollectionIfMissing).toHaveBeenCalledWith(grupoProdutoCollection, grupoProduto);
      expect(comp.grupoProdutosCollection).toEqual(expectedCollection);
    });

    it('Should call subGrupoProduto query and add missing value', () => {
      const estoque: IEstoque = { id: 456 };
      const subGrupoProduto: ISubGrupoProduto = { id: 79946 };
      estoque.subGrupoProduto = subGrupoProduto;

      const subGrupoProdutoCollection: ISubGrupoProduto[] = [{ id: 25446 }];
      jest.spyOn(subGrupoProdutoService, 'query').mockReturnValue(of(new HttpResponse({ body: subGrupoProdutoCollection })));
      const expectedCollection: ISubGrupoProduto[] = [subGrupoProduto, ...subGrupoProdutoCollection];
      jest.spyOn(subGrupoProdutoService, 'addSubGrupoProdutoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ estoque });
      comp.ngOnInit();

      expect(subGrupoProdutoService.query).toHaveBeenCalled();
      expect(subGrupoProdutoService.addSubGrupoProdutoToCollectionIfMissing).toHaveBeenCalledWith(
        subGrupoProdutoCollection,
        subGrupoProduto
      );
      expect(comp.subGrupoProdutosCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const estoque: IEstoque = { id: 456 };
      const produto: IProduto = { id: 27734 };
      estoque.produto = produto;
      const grupoProduto: IGrupoProduto = { id: 62777 };
      estoque.grupoProduto = grupoProduto;
      const subGrupoProduto: ISubGrupoProduto = { id: 49963 };
      estoque.subGrupoProduto = subGrupoProduto;

      activatedRoute.data = of({ estoque });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(estoque));
      expect(comp.produtosCollection).toContain(produto);
      expect(comp.grupoProdutosCollection).toContain(grupoProduto);
      expect(comp.subGrupoProdutosCollection).toContain(subGrupoProduto);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Estoque>>();
      const estoque = { id: 123 };
      jest.spyOn(estoqueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ estoque });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: estoque }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(estoqueService.update).toHaveBeenCalledWith(estoque);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Estoque>>();
      const estoque = new Estoque();
      jest.spyOn(estoqueService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ estoque });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: estoque }));
      saveSubject.complete();

      // THEN
      expect(estoqueService.create).toHaveBeenCalledWith(estoque);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Estoque>>();
      const estoque = { id: 123 };
      jest.spyOn(estoqueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ estoque });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(estoqueService.update).toHaveBeenCalledWith(estoque);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackProdutoById', () => {
      it('Should return tracked Produto primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProdutoById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackGrupoProdutoById', () => {
      it('Should return tracked GrupoProduto primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackGrupoProdutoById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSubGrupoProdutoById', () => {
      it('Should return tracked SubGrupoProduto primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSubGrupoProdutoById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
