import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FotoProdutoService } from '../service/foto-produto.service';
import { IFotoProduto, FotoProduto } from '../foto-produto.model';
import { IProduto } from 'app/entities/controle/produto/produto.model';
import { ProdutoService } from 'app/entities/controle/produto/service/produto.service';

import { FotoProdutoUpdateComponent } from './foto-produto-update.component';

describe('FotoProduto Management Update Component', () => {
  let comp: FotoProdutoUpdateComponent;
  let fixture: ComponentFixture<FotoProdutoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fotoProdutoService: FotoProdutoService;
  let produtoService: ProdutoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FotoProdutoUpdateComponent],
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
      .overrideTemplate(FotoProdutoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FotoProdutoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fotoProdutoService = TestBed.inject(FotoProdutoService);
    produtoService = TestBed.inject(ProdutoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Produto query and add missing value', () => {
      const fotoProduto: IFotoProduto = { id: 456 };
      const produto: IProduto = { id: 28588 };
      fotoProduto.produto = produto;

      const produtoCollection: IProduto[] = [{ id: 62899 }];
      jest.spyOn(produtoService, 'query').mockReturnValue(of(new HttpResponse({ body: produtoCollection })));
      const additionalProdutos = [produto];
      const expectedCollection: IProduto[] = [...additionalProdutos, ...produtoCollection];
      jest.spyOn(produtoService, 'addProdutoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fotoProduto });
      comp.ngOnInit();

      expect(produtoService.query).toHaveBeenCalled();
      expect(produtoService.addProdutoToCollectionIfMissing).toHaveBeenCalledWith(produtoCollection, ...additionalProdutos);
      expect(comp.produtosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const fotoProduto: IFotoProduto = { id: 456 };
      const produto: IProduto = { id: 70909 };
      fotoProduto.produto = produto;

      activatedRoute.data = of({ fotoProduto });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(fotoProduto));
      expect(comp.produtosSharedCollection).toContain(produto);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FotoProduto>>();
      const fotoProduto = { id: 123 };
      jest.spyOn(fotoProdutoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoProduto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fotoProduto }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(fotoProdutoService.update).toHaveBeenCalledWith(fotoProduto);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FotoProduto>>();
      const fotoProduto = new FotoProduto();
      jest.spyOn(fotoProdutoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoProduto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fotoProduto }));
      saveSubject.complete();

      // THEN
      expect(fotoProdutoService.create).toHaveBeenCalledWith(fotoProduto);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FotoProduto>>();
      const fotoProduto = { id: 123 };
      jest.spyOn(fotoProdutoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoProduto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fotoProdutoService.update).toHaveBeenCalledWith(fotoProduto);
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
  });
});
