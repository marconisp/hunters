import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ItemMateriaService } from '../service/item-materia.service';
import { IItemMateria, ItemMateria } from '../item-materia.model';
import { IMateria } from 'app/entities/matricula/materia/materia.model';
import { MateriaService } from 'app/entities/matricula/materia/service/materia.service';
import { IAcompanhamentoAluno } from 'app/entities/matricula/acompanhamento-aluno/acompanhamento-aluno.model';
import { AcompanhamentoAlunoService } from 'app/entities/matricula/acompanhamento-aluno/service/acompanhamento-aluno.service';

import { ItemMateriaUpdateComponent } from './item-materia-update.component';

describe('ItemMateria Management Update Component', () => {
  let comp: ItemMateriaUpdateComponent;
  let fixture: ComponentFixture<ItemMateriaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let itemMateriaService: ItemMateriaService;
  let materiaService: MateriaService;
  let acompanhamentoAlunoService: AcompanhamentoAlunoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ItemMateriaUpdateComponent],
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
      .overrideTemplate(ItemMateriaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ItemMateriaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    itemMateriaService = TestBed.inject(ItemMateriaService);
    materiaService = TestBed.inject(MateriaService);
    acompanhamentoAlunoService = TestBed.inject(AcompanhamentoAlunoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call materia query and add missing value', () => {
      const itemMateria: IItemMateria = { id: 456 };
      const materia: IMateria = { id: 12993 };
      itemMateria.materia = materia;

      const materiaCollection: IMateria[] = [{ id: 40669 }];
      jest.spyOn(materiaService, 'query').mockReturnValue(of(new HttpResponse({ body: materiaCollection })));
      const expectedCollection: IMateria[] = [materia, ...materiaCollection];
      jest.spyOn(materiaService, 'addMateriaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ itemMateria });
      comp.ngOnInit();

      expect(materiaService.query).toHaveBeenCalled();
      expect(materiaService.addMateriaToCollectionIfMissing).toHaveBeenCalledWith(materiaCollection, materia);
      expect(comp.materiasCollection).toEqual(expectedCollection);
    });

    it('Should call AcompanhamentoAluno query and add missing value', () => {
      const itemMateria: IItemMateria = { id: 456 };
      const acompanhamentoAluno: IAcompanhamentoAluno = { id: 71708 };
      itemMateria.acompanhamentoAluno = acompanhamentoAluno;

      const acompanhamentoAlunoCollection: IAcompanhamentoAluno[] = [{ id: 95706 }];
      jest.spyOn(acompanhamentoAlunoService, 'query').mockReturnValue(of(new HttpResponse({ body: acompanhamentoAlunoCollection })));
      const additionalAcompanhamentoAlunos = [acompanhamentoAluno];
      const expectedCollection: IAcompanhamentoAluno[] = [...additionalAcompanhamentoAlunos, ...acompanhamentoAlunoCollection];
      jest.spyOn(acompanhamentoAlunoService, 'addAcompanhamentoAlunoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ itemMateria });
      comp.ngOnInit();

      expect(acompanhamentoAlunoService.query).toHaveBeenCalled();
      expect(acompanhamentoAlunoService.addAcompanhamentoAlunoToCollectionIfMissing).toHaveBeenCalledWith(
        acompanhamentoAlunoCollection,
        ...additionalAcompanhamentoAlunos
      );
      expect(comp.acompanhamentoAlunosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const itemMateria: IItemMateria = { id: 456 };
      const materia: IMateria = { id: 93759 };
      itemMateria.materia = materia;
      const acompanhamentoAluno: IAcompanhamentoAluno = { id: 90339 };
      itemMateria.acompanhamentoAluno = acompanhamentoAluno;

      activatedRoute.data = of({ itemMateria });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(itemMateria));
      expect(comp.materiasCollection).toContain(materia);
      expect(comp.acompanhamentoAlunosSharedCollection).toContain(acompanhamentoAluno);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ItemMateria>>();
      const itemMateria = { id: 123 };
      jest.spyOn(itemMateriaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ itemMateria });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: itemMateria }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(itemMateriaService.update).toHaveBeenCalledWith(itemMateria);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ItemMateria>>();
      const itemMateria = new ItemMateria();
      jest.spyOn(itemMateriaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ itemMateria });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: itemMateria }));
      saveSubject.complete();

      // THEN
      expect(itemMateriaService.create).toHaveBeenCalledWith(itemMateria);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ItemMateria>>();
      const itemMateria = { id: 123 };
      jest.spyOn(itemMateriaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ itemMateria });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(itemMateriaService.update).toHaveBeenCalledWith(itemMateria);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackMateriaById', () => {
      it('Should return tracked Materia primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackMateriaById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackAcompanhamentoAlunoById', () => {
      it('Should return tracked AcompanhamentoAluno primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAcompanhamentoAlunoById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
