import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { GrupoProdutoService } from '../service/grupo-produto.service';
import { IGrupoProduto, GrupoProduto } from '../grupo-produto.model';

import { GrupoProdutoUpdateComponent } from './grupo-produto-update.component';

describe('GrupoProduto Management Update Component', () => {
  let comp: GrupoProdutoUpdateComponent;
  let fixture: ComponentFixture<GrupoProdutoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let grupoProdutoService: GrupoProdutoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [GrupoProdutoUpdateComponent],
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
      .overrideTemplate(GrupoProdutoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GrupoProdutoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    grupoProdutoService = TestBed.inject(GrupoProdutoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const grupoProduto: IGrupoProduto = { id: 456 };

      activatedRoute.data = of({ grupoProduto });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(grupoProduto));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<GrupoProduto>>();
      const grupoProduto = { id: 123 };
      jest.spyOn(grupoProdutoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ grupoProduto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: grupoProduto }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(grupoProdutoService.update).toHaveBeenCalledWith(grupoProduto);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<GrupoProduto>>();
      const grupoProduto = new GrupoProduto();
      jest.spyOn(grupoProdutoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ grupoProduto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: grupoProduto }));
      saveSubject.complete();

      // THEN
      expect(grupoProdutoService.create).toHaveBeenCalledWith(grupoProduto);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<GrupoProduto>>();
      const grupoProduto = { id: 123 };
      jest.spyOn(grupoProdutoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ grupoProduto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(grupoProdutoService.update).toHaveBeenCalledWith(grupoProduto);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
