import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EnderecoService } from '../service/endereco.service';
import { IEndereco, Endereco } from '../endereco.model';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';
import { DadosPessoaisService } from 'app/entities/user/dados-pessoais/service/dados-pessoais.service';

import { EnderecoUpdateComponent } from './endereco-update.component';

describe('Endereco Management Update Component', () => {
  let comp: EnderecoUpdateComponent;
  let fixture: ComponentFixture<EnderecoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let enderecoService: EnderecoService;
  let dadosPessoaisService: DadosPessoaisService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EnderecoUpdateComponent],
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
      .overrideTemplate(EnderecoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EnderecoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    enderecoService = TestBed.inject(EnderecoService);
    dadosPessoaisService = TestBed.inject(DadosPessoaisService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DadosPessoais query and add missing value', () => {
      const endereco: IEndereco = { id: 456 };
      const dadosPessoais: IDadosPessoais = { id: 36823 };
      endereco.dadosPessoais = dadosPessoais;

      const dadosPessoaisCollection: IDadosPessoais[] = [{ id: 79057 }];
      jest.spyOn(dadosPessoaisService, 'query').mockReturnValue(of(new HttpResponse({ body: dadosPessoaisCollection })));
      const additionalDadosPessoais = [dadosPessoais];
      const expectedCollection: IDadosPessoais[] = [...additionalDadosPessoais, ...dadosPessoaisCollection];
      jest.spyOn(dadosPessoaisService, 'addDadosPessoaisToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ endereco });
      comp.ngOnInit();

      expect(dadosPessoaisService.query).toHaveBeenCalled();
      expect(dadosPessoaisService.addDadosPessoaisToCollectionIfMissing).toHaveBeenCalledWith(
        dadosPessoaisCollection,
        ...additionalDadosPessoais
      );
      expect(comp.dadosPessoaisSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const endereco: IEndereco = { id: 456 };
      const dadosPessoais: IDadosPessoais = { id: 84668 };
      endereco.dadosPessoais = dadosPessoais;

      activatedRoute.data = of({ endereco });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(endereco));
      expect(comp.dadosPessoaisSharedCollection).toContain(dadosPessoais);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Endereco>>();
      const endereco = { id: 123 };
      jest.spyOn(enderecoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ endereco });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: endereco }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(enderecoService.update).toHaveBeenCalledWith(endereco);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Endereco>>();
      const endereco = new Endereco();
      jest.spyOn(enderecoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ endereco });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: endereco }));
      saveSubject.complete();

      // THEN
      expect(enderecoService.create).toHaveBeenCalledWith(endereco);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Endereco>>();
      const endereco = { id: 123 };
      jest.spyOn(enderecoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ endereco });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(enderecoService.update).toHaveBeenCalledWith(endereco);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackDadosPessoaisById', () => {
      it('Should return tracked DadosPessoais primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDadosPessoaisById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
