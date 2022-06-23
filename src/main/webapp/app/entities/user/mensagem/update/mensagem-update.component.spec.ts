import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MensagemService } from '../service/mensagem.service';
import { IMensagem, Mensagem } from '../mensagem.model';
import { ITipoMensagem } from 'app/entities/config/tipo-mensagem/tipo-mensagem.model';
import { TipoMensagemService } from 'app/entities/config/tipo-mensagem/service/tipo-mensagem.service';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';
import { DadosPessoaisService } from 'app/entities/user/dados-pessoais/service/dados-pessoais.service';

import { MensagemUpdateComponent } from './mensagem-update.component';

describe('Mensagem Management Update Component', () => {
  let comp: MensagemUpdateComponent;
  let fixture: ComponentFixture<MensagemUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let mensagemService: MensagemService;
  let tipoMensagemService: TipoMensagemService;
  let dadosPessoaisService: DadosPessoaisService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MensagemUpdateComponent],
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
      .overrideTemplate(MensagemUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MensagemUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    mensagemService = TestBed.inject(MensagemService);
    tipoMensagemService = TestBed.inject(TipoMensagemService);
    dadosPessoaisService = TestBed.inject(DadosPessoaisService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call tipo query and add missing value', () => {
      const mensagem: IMensagem = { id: 456 };
      const tipo: ITipoMensagem = { id: 85569 };
      mensagem.tipo = tipo;

      const tipoCollection: ITipoMensagem[] = [{ id: 55922 }];
      jest.spyOn(tipoMensagemService, 'query').mockReturnValue(of(new HttpResponse({ body: tipoCollection })));
      const expectedCollection: ITipoMensagem[] = [tipo, ...tipoCollection];
      jest.spyOn(tipoMensagemService, 'addTipoMensagemToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mensagem });
      comp.ngOnInit();

      expect(tipoMensagemService.query).toHaveBeenCalled();
      expect(tipoMensagemService.addTipoMensagemToCollectionIfMissing).toHaveBeenCalledWith(tipoCollection, tipo);
      expect(comp.tiposCollection).toEqual(expectedCollection);
    });

    it('Should call DadosPessoais query and add missing value', () => {
      const mensagem: IMensagem = { id: 456 };
      const dadosPessoais: IDadosPessoais = { id: 47110 };
      mensagem.dadosPessoais = dadosPessoais;

      const dadosPessoaisCollection: IDadosPessoais[] = [{ id: 73046 }];
      jest.spyOn(dadosPessoaisService, 'query').mockReturnValue(of(new HttpResponse({ body: dadosPessoaisCollection })));
      const additionalDadosPessoais = [dadosPessoais];
      const expectedCollection: IDadosPessoais[] = [...additionalDadosPessoais, ...dadosPessoaisCollection];
      jest.spyOn(dadosPessoaisService, 'addDadosPessoaisToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mensagem });
      comp.ngOnInit();

      expect(dadosPessoaisService.query).toHaveBeenCalled();
      expect(dadosPessoaisService.addDadosPessoaisToCollectionIfMissing).toHaveBeenCalledWith(
        dadosPessoaisCollection,
        ...additionalDadosPessoais
      );
      expect(comp.dadosPessoaisSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const mensagem: IMensagem = { id: 456 };
      const tipo: ITipoMensagem = { id: 2006 };
      mensagem.tipo = tipo;
      const dadosPessoais: IDadosPessoais = { id: 75577 };
      mensagem.dadosPessoais = dadosPessoais;

      activatedRoute.data = of({ mensagem });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(mensagem));
      expect(comp.tiposCollection).toContain(tipo);
      expect(comp.dadosPessoaisSharedCollection).toContain(dadosPessoais);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Mensagem>>();
      const mensagem = { id: 123 };
      jest.spyOn(mensagemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mensagem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mensagem }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(mensagemService.update).toHaveBeenCalledWith(mensagem);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Mensagem>>();
      const mensagem = new Mensagem();
      jest.spyOn(mensagemService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mensagem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mensagem }));
      saveSubject.complete();

      // THEN
      expect(mensagemService.create).toHaveBeenCalledWith(mensagem);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Mensagem>>();
      const mensagem = { id: 123 };
      jest.spyOn(mensagemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mensagem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(mensagemService.update).toHaveBeenCalledWith(mensagem);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackTipoMensagemById', () => {
      it('Should return tracked TipoMensagem primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTipoMensagemById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackDadosPessoaisById', () => {
      it('Should return tracked DadosPessoais primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDadosPessoaisById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
