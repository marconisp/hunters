import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AvaliacaoEconomicaService } from '../service/avaliacao-economica.service';
import { IAvaliacaoEconomica, AvaliacaoEconomica } from '../avaliacao-economica.model';
import { IDadosPessoais1 } from 'app/entities/user/dados-pessoais-1/dados-pessoais-1.model';
import { DadosPessoais1Service } from 'app/entities/user/dados-pessoais-1/service/dados-pessoais-1.service';

import { AvaliacaoEconomicaUpdateComponent } from './avaliacao-economica-update.component';

describe('AvaliacaoEconomica Management Update Component', () => {
  let comp: AvaliacaoEconomicaUpdateComponent;
  let fixture: ComponentFixture<AvaliacaoEconomicaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let avaliacaoEconomicaService: AvaliacaoEconomicaService;
  let dadosPessoais1Service: DadosPessoais1Service;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AvaliacaoEconomicaUpdateComponent],
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
      .overrideTemplate(AvaliacaoEconomicaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AvaliacaoEconomicaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    avaliacaoEconomicaService = TestBed.inject(AvaliacaoEconomicaService);
    dadosPessoais1Service = TestBed.inject(DadosPessoais1Service);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DadosPessoais1 query and add missing value', () => {
      const avaliacaoEconomica: IAvaliacaoEconomica = { id: 456 };
      const dadosPessoais1: IDadosPessoais1 = { id: 845 };
      avaliacaoEconomica.dadosPessoais1 = dadosPessoais1;

      const dadosPessoais1Collection: IDadosPessoais1[] = [{ id: 80004 }];
      jest.spyOn(dadosPessoais1Service, 'query').mockReturnValue(of(new HttpResponse({ body: dadosPessoais1Collection })));
      const additionalDadosPessoais1s = [dadosPessoais1];
      const expectedCollection: IDadosPessoais1[] = [...additionalDadosPessoais1s, ...dadosPessoais1Collection];
      jest.spyOn(dadosPessoais1Service, 'addDadosPessoais1ToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ avaliacaoEconomica });
      comp.ngOnInit();

      expect(dadosPessoais1Service.query).toHaveBeenCalled();
      expect(dadosPessoais1Service.addDadosPessoais1ToCollectionIfMissing).toHaveBeenCalledWith(
        dadosPessoais1Collection,
        ...additionalDadosPessoais1s
      );
      expect(comp.dadosPessoais1sSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const avaliacaoEconomica: IAvaliacaoEconomica = { id: 456 };
      const dadosPessoais1: IDadosPessoais1 = { id: 87766 };
      avaliacaoEconomica.dadosPessoais1 = dadosPessoais1;

      activatedRoute.data = of({ avaliacaoEconomica });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(avaliacaoEconomica));
      expect(comp.dadosPessoais1sSharedCollection).toContain(dadosPessoais1);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AvaliacaoEconomica>>();
      const avaliacaoEconomica = { id: 123 };
      jest.spyOn(avaliacaoEconomicaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ avaliacaoEconomica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: avaliacaoEconomica }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(avaliacaoEconomicaService.update).toHaveBeenCalledWith(avaliacaoEconomica);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AvaliacaoEconomica>>();
      const avaliacaoEconomica = new AvaliacaoEconomica();
      jest.spyOn(avaliacaoEconomicaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ avaliacaoEconomica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: avaliacaoEconomica }));
      saveSubject.complete();

      // THEN
      expect(avaliacaoEconomicaService.create).toHaveBeenCalledWith(avaliacaoEconomica);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AvaliacaoEconomica>>();
      const avaliacaoEconomica = { id: 123 };
      jest.spyOn(avaliacaoEconomicaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ avaliacaoEconomica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(avaliacaoEconomicaService.update).toHaveBeenCalledWith(avaliacaoEconomica);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackDadosPessoais1ById', () => {
      it('Should return tracked DadosPessoais1 primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDadosPessoais1ById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
