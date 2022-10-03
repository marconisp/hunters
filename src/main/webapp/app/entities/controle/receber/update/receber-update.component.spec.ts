import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ReceberService } from '../service/receber.service';
import { IReceber, Receber } from '../receber.model';
import { ITipoReceber } from 'app/entities/controle/tipo-receber/tipo-receber.model';
import { TipoReceberService } from 'app/entities/controle/tipo-receber/service/tipo-receber.service';
import { IReceberDe } from 'app/entities/controle/receber-de/receber-de.model';
import { ReceberDeService } from 'app/entities/controle/receber-de/service/receber-de.service';
import { ITipoTransacao } from 'app/entities/controle/tipo-transacao/tipo-transacao.model';
import { TipoTransacaoService } from 'app/entities/controle/tipo-transacao/service/tipo-transacao.service';
import { IDadosPessoais1 } from 'app/entities/user/dados-pessoais-1/dados-pessoais-1.model';
import { DadosPessoais1Service } from 'app/entities/user/dados-pessoais-1/service/dados-pessoais-1.service';

import { ReceberUpdateComponent } from './receber-update.component';

describe('Receber Management Update Component', () => {
  let comp: ReceberUpdateComponent;
  let fixture: ComponentFixture<ReceberUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let receberService: ReceberService;
  let tipoReceberService: TipoReceberService;
  let receberDeService: ReceberDeService;
  let tipoTransacaoService: TipoTransacaoService;
  let dadosPessoais1Service: DadosPessoais1Service;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ReceberUpdateComponent],
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
      .overrideTemplate(ReceberUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReceberUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    receberService = TestBed.inject(ReceberService);
    tipoReceberService = TestBed.inject(TipoReceberService);
    receberDeService = TestBed.inject(ReceberDeService);
    tipoTransacaoService = TestBed.inject(TipoTransacaoService);
    dadosPessoais1Service = TestBed.inject(DadosPessoais1Service);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call tipoReceber query and add missing value', () => {
      const receber: IReceber = { id: 456 };
      const tipoReceber: ITipoReceber = { id: 21168 };
      receber.tipoReceber = tipoReceber;

      const tipoReceberCollection: ITipoReceber[] = [{ id: 94133 }];
      jest.spyOn(tipoReceberService, 'query').mockReturnValue(of(new HttpResponse({ body: tipoReceberCollection })));
      const expectedCollection: ITipoReceber[] = [tipoReceber, ...tipoReceberCollection];
      jest.spyOn(tipoReceberService, 'addTipoReceberToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ receber });
      comp.ngOnInit();

      expect(tipoReceberService.query).toHaveBeenCalled();
      expect(tipoReceberService.addTipoReceberToCollectionIfMissing).toHaveBeenCalledWith(tipoReceberCollection, tipoReceber);
      expect(comp.tipoRecebersCollection).toEqual(expectedCollection);
    });

    it('Should call receberDe query and add missing value', () => {
      const receber: IReceber = { id: 456 };
      const receberDe: IReceberDe = { id: 53405 };
      receber.receberDe = receberDe;

      const receberDeCollection: IReceberDe[] = [{ id: 58326 }];
      jest.spyOn(receberDeService, 'query').mockReturnValue(of(new HttpResponse({ body: receberDeCollection })));
      const expectedCollection: IReceberDe[] = [receberDe, ...receberDeCollection];
      jest.spyOn(receberDeService, 'addReceberDeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ receber });
      comp.ngOnInit();

      expect(receberDeService.query).toHaveBeenCalled();
      expect(receberDeService.addReceberDeToCollectionIfMissing).toHaveBeenCalledWith(receberDeCollection, receberDe);
      expect(comp.receberDesCollection).toEqual(expectedCollection);
    });

    it('Should call tipoTransacao query and add missing value', () => {
      const receber: IReceber = { id: 456 };
      const tipoTransacao: ITipoTransacao = { id: 16786 };
      receber.tipoTransacao = tipoTransacao;

      const tipoTransacaoCollection: ITipoTransacao[] = [{ id: 99653 }];
      jest.spyOn(tipoTransacaoService, 'query').mockReturnValue(of(new HttpResponse({ body: tipoTransacaoCollection })));
      const expectedCollection: ITipoTransacao[] = [tipoTransacao, ...tipoTransacaoCollection];
      jest.spyOn(tipoTransacaoService, 'addTipoTransacaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ receber });
      comp.ngOnInit();

      expect(tipoTransacaoService.query).toHaveBeenCalled();
      expect(tipoTransacaoService.addTipoTransacaoToCollectionIfMissing).toHaveBeenCalledWith(tipoTransacaoCollection, tipoTransacao);
      expect(comp.tipoTransacaosCollection).toEqual(expectedCollection);
    });

    it('Should call DadosPessoais1 query and add missing value', () => {
      const receber: IReceber = { id: 456 };
      const dadosPessoais1: IDadosPessoais1 = { id: 91488 };
      receber.dadosPessoais1 = dadosPessoais1;

      const dadosPessoais1Collection: IDadosPessoais1[] = [{ id: 99941 }];
      jest.spyOn(dadosPessoais1Service, 'query').mockReturnValue(of(new HttpResponse({ body: dadosPessoais1Collection })));
      const additionalDadosPessoais1s = [dadosPessoais1];
      const expectedCollection: IDadosPessoais1[] = [...additionalDadosPessoais1s, ...dadosPessoais1Collection];
      jest.spyOn(dadosPessoais1Service, 'addDadosPessoais1ToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ receber });
      comp.ngOnInit();

      expect(dadosPessoais1Service.query).toHaveBeenCalled();
      expect(dadosPessoais1Service.addDadosPessoais1ToCollectionIfMissing).toHaveBeenCalledWith(
        dadosPessoais1Collection,
        ...additionalDadosPessoais1s
      );
      expect(comp.dadosPessoais1sSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const receber: IReceber = { id: 456 };
      const tipoReceber: ITipoReceber = { id: 92423 };
      receber.tipoReceber = tipoReceber;
      const receberDe: IReceberDe = { id: 41274 };
      receber.receberDe = receberDe;
      const tipoTransacao: ITipoTransacao = { id: 14710 };
      receber.tipoTransacao = tipoTransacao;
      const dadosPessoais1: IDadosPessoais1 = { id: 4564 };
      receber.dadosPessoais1 = dadosPessoais1;

      activatedRoute.data = of({ receber });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(receber));
      expect(comp.tipoRecebersCollection).toContain(tipoReceber);
      expect(comp.receberDesCollection).toContain(receberDe);
      expect(comp.tipoTransacaosCollection).toContain(tipoTransacao);
      expect(comp.dadosPessoais1sSharedCollection).toContain(dadosPessoais1);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Receber>>();
      const receber = { id: 123 };
      jest.spyOn(receberService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ receber });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: receber }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(receberService.update).toHaveBeenCalledWith(receber);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Receber>>();
      const receber = new Receber();
      jest.spyOn(receberService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ receber });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: receber }));
      saveSubject.complete();

      // THEN
      expect(receberService.create).toHaveBeenCalledWith(receber);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Receber>>();
      const receber = { id: 123 };
      jest.spyOn(receberService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ receber });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(receberService.update).toHaveBeenCalledWith(receber);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackTipoReceberById', () => {
      it('Should return tracked TipoReceber primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTipoReceberById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackReceberDeById', () => {
      it('Should return tracked ReceberDe primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackReceberDeById(0, entity);
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
