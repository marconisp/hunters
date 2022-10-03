import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EntradaEstoqueService } from '../service/entrada-estoque.service';
import { IEntradaEstoque, EntradaEstoque } from '../entrada-estoque.model';
import { IProduto } from 'app/entities/controle/produto/produto.model';
import { ProdutoService } from 'app/entities/controle/produto/service/produto.service';
import { IDadosPessoais1 } from 'app/entities/user/dados-pessoais-1/dados-pessoais-1.model';
import { DadosPessoais1Service } from 'app/entities/user/dados-pessoais-1/service/dados-pessoais-1.service';

import { EntradaEstoqueUpdateComponent } from './entrada-estoque-update.component';

describe('EntradaEstoque Management Update Component', () => {
  let comp: EntradaEstoqueUpdateComponent;
  let fixture: ComponentFixture<EntradaEstoqueUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let entradaEstoqueService: EntradaEstoqueService;
  let produtoService: ProdutoService;
  let dadosPessoais1Service: DadosPessoais1Service;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EntradaEstoqueUpdateComponent],
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
      .overrideTemplate(EntradaEstoqueUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EntradaEstoqueUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    entradaEstoqueService = TestBed.inject(EntradaEstoqueService);
    produtoService = TestBed.inject(ProdutoService);
    dadosPessoais1Service = TestBed.inject(DadosPessoais1Service);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call produto query and add missing value', () => {
      const entradaEstoque: IEntradaEstoque = { id: 456 };
      const produto: IProduto = { id: 54211 };
      entradaEstoque.produto = produto;

      const produtoCollection: IProduto[] = [{ id: 23315 }];
      jest.spyOn(produtoService, 'query').mockReturnValue(of(new HttpResponse({ body: produtoCollection })));
      const expectedCollection: IProduto[] = [produto, ...produtoCollection];
      jest.spyOn(produtoService, 'addProdutoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ entradaEstoque });
      comp.ngOnInit();

      expect(produtoService.query).toHaveBeenCalled();
      expect(produtoService.addProdutoToCollectionIfMissing).toHaveBeenCalledWith(produtoCollection, produto);
      expect(comp.produtosCollection).toEqual(expectedCollection);
    });

    it('Should call DadosPessoais1 query and add missing value', () => {
      const entradaEstoque: IEntradaEstoque = { id: 456 };
      const dadosPessoais1: IDadosPessoais1 = { id: 48064 };
      entradaEstoque.dadosPessoais1 = dadosPessoais1;

      const dadosPessoais1Collection: IDadosPessoais1[] = [{ id: 68814 }];
      jest.spyOn(dadosPessoais1Service, 'query').mockReturnValue(of(new HttpResponse({ body: dadosPessoais1Collection })));
      const additionalDadosPessoais1s = [dadosPessoais1];
      const expectedCollection: IDadosPessoais1[] = [...additionalDadosPessoais1s, ...dadosPessoais1Collection];
      jest.spyOn(dadosPessoais1Service, 'addDadosPessoais1ToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ entradaEstoque });
      comp.ngOnInit();

      expect(dadosPessoais1Service.query).toHaveBeenCalled();
      expect(dadosPessoais1Service.addDadosPessoais1ToCollectionIfMissing).toHaveBeenCalledWith(
        dadosPessoais1Collection,
        ...additionalDadosPessoais1s
      );
      expect(comp.dadosPessoais1sSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const entradaEstoque: IEntradaEstoque = { id: 456 };
      const produto: IProduto = { id: 53180 };
      entradaEstoque.produto = produto;
      const dadosPessoais1: IDadosPessoais1 = { id: 27979 };
      entradaEstoque.dadosPessoais1 = dadosPessoais1;

      activatedRoute.data = of({ entradaEstoque });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(entradaEstoque));
      expect(comp.produtosCollection).toContain(produto);
      expect(comp.dadosPessoais1sSharedCollection).toContain(dadosPessoais1);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EntradaEstoque>>();
      const entradaEstoque = { id: 123 };
      jest.spyOn(entradaEstoqueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ entradaEstoque });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: entradaEstoque }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(entradaEstoqueService.update).toHaveBeenCalledWith(entradaEstoque);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EntradaEstoque>>();
      const entradaEstoque = new EntradaEstoque();
      jest.spyOn(entradaEstoqueService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ entradaEstoque });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: entradaEstoque }));
      saveSubject.complete();

      // THEN
      expect(entradaEstoqueService.create).toHaveBeenCalledWith(entradaEstoque);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EntradaEstoque>>();
      const entradaEstoque = { id: 123 };
      jest.spyOn(entradaEstoqueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ entradaEstoque });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(entradaEstoqueService.update).toHaveBeenCalledWith(entradaEstoque);
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

    describe('trackDadosPessoais1ById', () => {
      it('Should return tracked DadosPessoais1 primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDadosPessoais1ById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
