import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AcompanhamentoAlunoService } from '../service/acompanhamento-aluno.service';
import { IAcompanhamentoAluno, AcompanhamentoAluno } from '../acompanhamento-aluno.model';
import { IDadosPessoais1 } from 'app/entities/user/dados-pessoais-1/dados-pessoais-1.model';
import { DadosPessoais1Service } from 'app/entities/user/dados-pessoais-1/service/dados-pessoais-1.service';

import { AcompanhamentoAlunoUpdateComponent } from './acompanhamento-aluno-update.component';

describe('AcompanhamentoAluno Management Update Component', () => {
  let comp: AcompanhamentoAlunoUpdateComponent;
  let fixture: ComponentFixture<AcompanhamentoAlunoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let acompanhamentoAlunoService: AcompanhamentoAlunoService;
  let dadosPessoais1Service: DadosPessoais1Service;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AcompanhamentoAlunoUpdateComponent],
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
      .overrideTemplate(AcompanhamentoAlunoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AcompanhamentoAlunoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    acompanhamentoAlunoService = TestBed.inject(AcompanhamentoAlunoService);
    dadosPessoais1Service = TestBed.inject(DadosPessoais1Service);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DadosPessoais1 query and add missing value', () => {
      const acompanhamentoAluno: IAcompanhamentoAluno = { id: 456 };
      const dadosPessoais1: IDadosPessoais1 = { id: 24187 };
      acompanhamentoAluno.dadosPessoais1 = dadosPessoais1;

      const dadosPessoais1Collection: IDadosPessoais1[] = [{ id: 24665 }];
      jest.spyOn(dadosPessoais1Service, 'query').mockReturnValue(of(new HttpResponse({ body: dadosPessoais1Collection })));
      const additionalDadosPessoais1s = [dadosPessoais1];
      const expectedCollection: IDadosPessoais1[] = [...additionalDadosPessoais1s, ...dadosPessoais1Collection];
      jest.spyOn(dadosPessoais1Service, 'addDadosPessoais1ToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ acompanhamentoAluno });
      comp.ngOnInit();

      expect(dadosPessoais1Service.query).toHaveBeenCalled();
      expect(dadosPessoais1Service.addDadosPessoais1ToCollectionIfMissing).toHaveBeenCalledWith(
        dadosPessoais1Collection,
        ...additionalDadosPessoais1s
      );
      expect(comp.dadosPessoais1sSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const acompanhamentoAluno: IAcompanhamentoAluno = { id: 456 };
      const dadosPessoais1: IDadosPessoais1 = { id: 94927 };
      acompanhamentoAluno.dadosPessoais1 = dadosPessoais1;

      activatedRoute.data = of({ acompanhamentoAluno });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(acompanhamentoAluno));
      expect(comp.dadosPessoais1sSharedCollection).toContain(dadosPessoais1);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AcompanhamentoAluno>>();
      const acompanhamentoAluno = { id: 123 };
      jest.spyOn(acompanhamentoAlunoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ acompanhamentoAluno });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: acompanhamentoAluno }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(acompanhamentoAlunoService.update).toHaveBeenCalledWith(acompanhamentoAluno);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AcompanhamentoAluno>>();
      const acompanhamentoAluno = new AcompanhamentoAluno();
      jest.spyOn(acompanhamentoAlunoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ acompanhamentoAluno });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: acompanhamentoAluno }));
      saveSubject.complete();

      // THEN
      expect(acompanhamentoAlunoService.create).toHaveBeenCalledWith(acompanhamentoAluno);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AcompanhamentoAluno>>();
      const acompanhamentoAluno = { id: 123 };
      jest.spyOn(acompanhamentoAlunoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ acompanhamentoAluno });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(acompanhamentoAlunoService.update).toHaveBeenCalledWith(acompanhamentoAluno);
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
