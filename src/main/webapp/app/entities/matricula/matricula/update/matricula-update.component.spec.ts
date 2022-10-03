import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MatriculaService } from '../service/matricula.service';
import { IMatricula, Matricula } from '../matricula.model';
import { ITurma } from 'app/entities/matricula/turma/turma.model';
import { TurmaService } from 'app/entities/matricula/turma/service/turma.service';
import { IDadosPessoais1 } from 'app/entities/user/dados-pessoais-1/dados-pessoais-1.model';
import { DadosPessoais1Service } from 'app/entities/user/dados-pessoais-1/service/dados-pessoais-1.service';

import { MatriculaUpdateComponent } from './matricula-update.component';

describe('Matricula Management Update Component', () => {
  let comp: MatriculaUpdateComponent;
  let fixture: ComponentFixture<MatriculaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let matriculaService: MatriculaService;
  let turmaService: TurmaService;
  let dadosPessoais1Service: DadosPessoais1Service;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MatriculaUpdateComponent],
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
      .overrideTemplate(MatriculaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MatriculaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    matriculaService = TestBed.inject(MatriculaService);
    turmaService = TestBed.inject(TurmaService);
    dadosPessoais1Service = TestBed.inject(DadosPessoais1Service);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call turma query and add missing value', () => {
      const matricula: IMatricula = { id: 456 };
      const turma: ITurma = { id: 46267 };
      matricula.turma = turma;

      const turmaCollection: ITurma[] = [{ id: 51978 }];
      jest.spyOn(turmaService, 'query').mockReturnValue(of(new HttpResponse({ body: turmaCollection })));
      const expectedCollection: ITurma[] = [turma, ...turmaCollection];
      jest.spyOn(turmaService, 'addTurmaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ matricula });
      comp.ngOnInit();

      expect(turmaService.query).toHaveBeenCalled();
      expect(turmaService.addTurmaToCollectionIfMissing).toHaveBeenCalledWith(turmaCollection, turma);
      expect(comp.turmasCollection).toEqual(expectedCollection);
    });

    it('Should call DadosPessoais1 query and add missing value', () => {
      const matricula: IMatricula = { id: 456 };
      const dadosPessoais1: IDadosPessoais1 = { id: 81552 };
      matricula.dadosPessoais1 = dadosPessoais1;

      const dadosPessoais1Collection: IDadosPessoais1[] = [{ id: 86819 }];
      jest.spyOn(dadosPessoais1Service, 'query').mockReturnValue(of(new HttpResponse({ body: dadosPessoais1Collection })));
      const additionalDadosPessoais1s = [dadosPessoais1];
      const expectedCollection: IDadosPessoais1[] = [...additionalDadosPessoais1s, ...dadosPessoais1Collection];
      jest.spyOn(dadosPessoais1Service, 'addDadosPessoais1ToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ matricula });
      comp.ngOnInit();

      expect(dadosPessoais1Service.query).toHaveBeenCalled();
      expect(dadosPessoais1Service.addDadosPessoais1ToCollectionIfMissing).toHaveBeenCalledWith(
        dadosPessoais1Collection,
        ...additionalDadosPessoais1s
      );
      expect(comp.dadosPessoais1sSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const matricula: IMatricula = { id: 456 };
      const turma: ITurma = { id: 63808 };
      matricula.turma = turma;
      const dadosPessoais1: IDadosPessoais1 = { id: 55316 };
      matricula.dadosPessoais1 = dadosPessoais1;

      activatedRoute.data = of({ matricula });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(matricula));
      expect(comp.turmasCollection).toContain(turma);
      expect(comp.dadosPessoais1sSharedCollection).toContain(dadosPessoais1);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Matricula>>();
      const matricula = { id: 123 };
      jest.spyOn(matriculaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ matricula });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: matricula }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(matriculaService.update).toHaveBeenCalledWith(matricula);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Matricula>>();
      const matricula = new Matricula();
      jest.spyOn(matriculaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ matricula });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: matricula }));
      saveSubject.complete();

      // THEN
      expect(matriculaService.create).toHaveBeenCalledWith(matricula);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Matricula>>();
      const matricula = { id: 123 };
      jest.spyOn(matriculaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ matricula });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(matriculaService.update).toHaveBeenCalledWith(matricula);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackTurmaById', () => {
      it('Should return tracked Turma primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTurmaById(0, entity);
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
