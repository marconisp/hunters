import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SaidaEstoqueService } from '../service/saida-estoque.service';
import { ISaidaEstoque, SaidaEstoque } from '../saida-estoque.model';
import { IProduto } from 'app/entities/controle/produto/produto.model';
import { ProdutoService } from 'app/entities/controle/produto/service/produto.service';
import { IDadosPessoais1 } from 'app/entities/user/dados-pessoais-1/dados-pessoais-1.model';
import { DadosPessoais1Service } from 'app/entities/user/dados-pessoais-1/service/dados-pessoais-1.service';

import { SaidaEstoqueUpdateComponent } from './saida-estoque-update.component';

describe('SaidaEstoque Management Update Component', () => {
  let comp: SaidaEstoqueUpdateComponent;
  let fixture: ComponentFixture<SaidaEstoqueUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let saidaEstoqueService: SaidaEstoqueService;
  let produtoService: ProdutoService;
  let dadosPessoais1Service: DadosPessoais1Service;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SaidaEstoqueUpdateComponent],
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
      .overrideTemplate(SaidaEstoqueUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SaidaEstoqueUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    saidaEstoqueService = TestBed.inject(SaidaEstoqueService);
    produtoService = TestBed.inject(ProdutoService);
    dadosPessoais1Service = TestBed.inject(DadosPessoais1Service);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call produto query and add missing value', () => {
      const saidaEstoque: ISaidaEstoque = { id: 456 };
      const produto: IProduto = { id: 55806 };
      saidaEstoque.produto = produto;

      const produtoCollection: IProduto[] = [{ id: 25407 }];
      jest.spyOn(produtoService, 'query').mockReturnValue(of(new HttpResponse({ body: produtoCollection })));
      const expectedCollection: IProduto[] = [produto, ...produtoCollection];
      jest.spyOn(produtoService, 'addProdutoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ saidaEstoque });
      comp.ngOnInit();

      expect(produtoService.query).toHaveBeenCalled();
      expect(produtoService.addProdutoToCollectionIfMissing).toHaveBeenCalledWith(produtoCollection, produto);
      expect(comp.produtosCollection).toEqual(expectedCollection);
    });

    it('Should call DadosPessoais1 query and add missing value', () => {
      const saidaEstoque: ISaidaEstoque = { id: 456 };
      const dadosPessoais1: IDadosPessoais1 = { id: 88020 };
      saidaEstoque.dadosPessoais1 = dadosPessoais1;

      const dadosPessoais1Collection: IDadosPessoais1[] = [{ id: 15807 }];
      jest.spyOn(dadosPessoais1Service, 'query').mockReturnValue(of(new HttpResponse({ body: dadosPessoais1Collection })));
      const additionalDadosPessoais1s = [dadosPessoais1];
      const expectedCollection: IDadosPessoais1[] = [...additionalDadosPessoais1s, ...dadosPessoais1Collection];
      jest.spyOn(dadosPessoais1Service, 'addDadosPessoais1ToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ saidaEstoque });
      comp.ngOnInit();

      expect(dadosPessoais1Service.query).toHaveBeenCalled();
      expect(dadosPessoais1Service.addDadosPessoais1ToCollectionIfMissing).toHaveBeenCalledWith(
        dadosPessoais1Collection,
        ...additionalDadosPessoais1s
      );
      expect(comp.dadosPessoais1sSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const saidaEstoque: ISaidaEstoque = { id: 456 };
      const produto: IProduto = { id: 85827 };
      saidaEstoque.produto = produto;
      const dadosPessoais1: IDadosPessoais1 = { id: 34029 };
      saidaEstoque.dadosPessoais1 = dadosPessoais1;

      activatedRoute.data = of({ saidaEstoque });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(saidaEstoque));
      expect(comp.produtosCollection).toContain(produto);
      expect(comp.dadosPessoais1sSharedCollection).toContain(dadosPessoais1);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SaidaEstoque>>();
      const saidaEstoque = { id: 123 };
      jest.spyOn(saidaEstoqueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ saidaEstoque });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: saidaEstoque }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(saidaEstoqueService.update).toHaveBeenCalledWith(saidaEstoque);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SaidaEstoque>>();
      const saidaEstoque = new SaidaEstoque();
      jest.spyOn(saidaEstoqueService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ saidaEstoque });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: saidaEstoque }));
      saveSubject.complete();

      // THEN
      expect(saidaEstoqueService.create).toHaveBeenCalledWith(saidaEstoque);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SaidaEstoque>>();
      const saidaEstoque = { id: 123 };
      jest.spyOn(saidaEstoqueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ saidaEstoque });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(saidaEstoqueService.update).toHaveBeenCalledWith(saidaEstoque);
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
