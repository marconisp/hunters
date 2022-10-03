import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PagarService } from '../service/pagar.service';
import { IPagar, Pagar } from '../pagar.model';
import { ITipoPagar } from 'app/entities/controle/tipo-pagar/tipo-pagar.model';
import { TipoPagarService } from 'app/entities/controle/tipo-pagar/service/tipo-pagar.service';
import { IPagarPara } from 'app/entities/controle/pagar-para/pagar-para.model';
import { PagarParaService } from 'app/entities/controle/pagar-para/service/pagar-para.service';
import { ITipoTransacao } from 'app/entities/controle/tipo-transacao/tipo-transacao.model';
import { TipoTransacaoService } from 'app/entities/controle/tipo-transacao/service/tipo-transacao.service';
import { IDadosPessoais1 } from 'app/entities/user/dados-pessoais-1/dados-pessoais-1.model';
import { DadosPessoais1Service } from 'app/entities/user/dados-pessoais-1/service/dados-pessoais-1.service';

import { PagarUpdateComponent } from './pagar-update.component';

describe('Pagar Management Update Component', () => {
  let comp: PagarUpdateComponent;
  let fixture: ComponentFixture<PagarUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pagarService: PagarService;
  let tipoPagarService: TipoPagarService;
  let pagarParaService: PagarParaService;
  let tipoTransacaoService: TipoTransacaoService;
  let dadosPessoais1Service: DadosPessoais1Service;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PagarUpdateComponent],
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
      .overrideTemplate(PagarUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PagarUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pagarService = TestBed.inject(PagarService);
    tipoPagarService = TestBed.inject(TipoPagarService);
    pagarParaService = TestBed.inject(PagarParaService);
    tipoTransacaoService = TestBed.inject(TipoTransacaoService);
    dadosPessoais1Service = TestBed.inject(DadosPessoais1Service);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call tipoPagar query and add missing value', () => {
      const pagar: IPagar = { id: 456 };
      const tipoPagar: ITipoPagar = { id: 58398 };
      pagar.tipoPagar = tipoPagar;

      const tipoPagarCollection: ITipoPagar[] = [{ id: 37136 }];
      jest.spyOn(tipoPagarService, 'query').mockReturnValue(of(new HttpResponse({ body: tipoPagarCollection })));
      const expectedCollection: ITipoPagar[] = [tipoPagar, ...tipoPagarCollection];
      jest.spyOn(tipoPagarService, 'addTipoPagarToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pagar });
      comp.ngOnInit();

      expect(tipoPagarService.query).toHaveBeenCalled();
      expect(tipoPagarService.addTipoPagarToCollectionIfMissing).toHaveBeenCalledWith(tipoPagarCollection, tipoPagar);
      expect(comp.tipoPagarsCollection).toEqual(expectedCollection);
    });

    it('Should call pagarPara query and add missing value', () => {
      const pagar: IPagar = { id: 456 };
      const pagarPara: IPagarPara = { id: 2932 };
      pagar.pagarPara = pagarPara;

      const pagarParaCollection: IPagarPara[] = [{ id: 97348 }];
      jest.spyOn(pagarParaService, 'query').mockReturnValue(of(new HttpResponse({ body: pagarParaCollection })));
      const expectedCollection: IPagarPara[] = [pagarPara, ...pagarParaCollection];
      jest.spyOn(pagarParaService, 'addPagarParaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pagar });
      comp.ngOnInit();

      expect(pagarParaService.query).toHaveBeenCalled();
      expect(pagarParaService.addPagarParaToCollectionIfMissing).toHaveBeenCalledWith(pagarParaCollection, pagarPara);
      expect(comp.pagarParasCollection).toEqual(expectedCollection);
    });

    it('Should call tipoTransacao query and add missing value', () => {
      const pagar: IPagar = { id: 456 };
      const tipoTransacao: ITipoTransacao = { id: 11128 };
      pagar.tipoTransacao = tipoTransacao;

      const tipoTransacaoCollection: ITipoTransacao[] = [{ id: 55045 }];
      jest.spyOn(tipoTransacaoService, 'query').mockReturnValue(of(new HttpResponse({ body: tipoTransacaoCollection })));
      const expectedCollection: ITipoTransacao[] = [tipoTransacao, ...tipoTransacaoCollection];
      jest.spyOn(tipoTransacaoService, 'addTipoTransacaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pagar });
      comp.ngOnInit();

      expect(tipoTransacaoService.query).toHaveBeenCalled();
      expect(tipoTransacaoService.addTipoTransacaoToCollectionIfMissing).toHaveBeenCalledWith(tipoTransacaoCollection, tipoTransacao);
      expect(comp.tipoTransacaosCollection).toEqual(expectedCollection);
    });

    it('Should call DadosPessoais1 query and add missing value', () => {
      const pagar: IPagar = { id: 456 };
      const dadosPessoais1: IDadosPessoais1 = { id: 54085 };
      pagar.dadosPessoais1 = dadosPessoais1;

      const dadosPessoais1Collection: IDadosPessoais1[] = [{ id: 19825 }];
      jest.spyOn(dadosPessoais1Service, 'query').mockReturnValue(of(new HttpResponse({ body: dadosPessoais1Collection })));
      const additionalDadosPessoais1s = [dadosPessoais1];
      const expectedCollection: IDadosPessoais1[] = [...additionalDadosPessoais1s, ...dadosPessoais1Collection];
      jest.spyOn(dadosPessoais1Service, 'addDadosPessoais1ToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pagar });
      comp.ngOnInit();

      expect(dadosPessoais1Service.query).toHaveBeenCalled();
      expect(dadosPessoais1Service.addDadosPessoais1ToCollectionIfMissing).toHaveBeenCalledWith(
        dadosPessoais1Collection,
        ...additionalDadosPessoais1s
      );
      expect(comp.dadosPessoais1sSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const pagar: IPagar = { id: 456 };
      const tipoPagar: ITipoPagar = { id: 56004 };
      pagar.tipoPagar = tipoPagar;
      const pagarPara: IPagarPara = { id: 11954 };
      pagar.pagarPara = pagarPara;
      const tipoTransacao: ITipoTransacao = { id: 50889 };
      pagar.tipoTransacao = tipoTransacao;
      const dadosPessoais1: IDadosPessoais1 = { id: 71679 };
      pagar.dadosPessoais1 = dadosPessoais1;

      activatedRoute.data = of({ pagar });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(pagar));
      expect(comp.tipoPagarsCollection).toContain(tipoPagar);
      expect(comp.pagarParasCollection).toContain(pagarPara);
      expect(comp.tipoTransacaosCollection).toContain(tipoTransacao);
      expect(comp.dadosPessoais1sSharedCollection).toContain(dadosPessoais1);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Pagar>>();
      const pagar = { id: 123 };
      jest.spyOn(pagarService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pagar });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pagar }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(pagarService.update).toHaveBeenCalledWith(pagar);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Pagar>>();
      const pagar = new Pagar();
      jest.spyOn(pagarService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pagar });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pagar }));
      saveSubject.complete();

      // THEN
      expect(pagarService.create).toHaveBeenCalledWith(pagar);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Pagar>>();
      const pagar = { id: 123 };
      jest.spyOn(pagarService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pagar });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pagarService.update).toHaveBeenCalledWith(pagar);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackTipoPagarById', () => {
      it('Should return tracked TipoPagar primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTipoPagarById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackPagarParaById', () => {
      it('Should return tracked PagarPara primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPagarParaById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackTipoTransacaoById', () => {
      it('Should return tracked TipoTransacao primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTipoTransacaoById(0, entity);
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
