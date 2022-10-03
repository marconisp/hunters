import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CaracteristicasPsiquicasService } from '../service/caracteristicas-psiquicas.service';
import { ICaracteristicasPsiquicas, CaracteristicasPsiquicas } from '../caracteristicas-psiquicas.model';
import { IDadosPessoais1 } from 'app/entities/user/dados-pessoais-1/dados-pessoais-1.model';
import { DadosPessoais1Service } from 'app/entities/user/dados-pessoais-1/service/dados-pessoais-1.service';

import { CaracteristicasPsiquicasUpdateComponent } from './caracteristicas-psiquicas-update.component';

describe('CaracteristicasPsiquicas Management Update Component', () => {
  let comp: CaracteristicasPsiquicasUpdateComponent;
  let fixture: ComponentFixture<CaracteristicasPsiquicasUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let caracteristicasPsiquicasService: CaracteristicasPsiquicasService;
  let dadosPessoais1Service: DadosPessoais1Service;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CaracteristicasPsiquicasUpdateComponent],
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
      .overrideTemplate(CaracteristicasPsiquicasUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CaracteristicasPsiquicasUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    caracteristicasPsiquicasService = TestBed.inject(CaracteristicasPsiquicasService);
    dadosPessoais1Service = TestBed.inject(DadosPessoais1Service);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DadosPessoais1 query and add missing value', () => {
      const caracteristicasPsiquicas: ICaracteristicasPsiquicas = { id: 456 };
      const dadosPessoais1: IDadosPessoais1 = { id: 17246 };
      caracteristicasPsiquicas.dadosPessoais1 = dadosPessoais1;

      const dadosPessoais1Collection: IDadosPessoais1[] = [{ id: 22222 }];
      jest.spyOn(dadosPessoais1Service, 'query').mockReturnValue(of(new HttpResponse({ body: dadosPessoais1Collection })));
      const additionalDadosPessoais1s = [dadosPessoais1];
      const expectedCollection: IDadosPessoais1[] = [...additionalDadosPessoais1s, ...dadosPessoais1Collection];
      jest.spyOn(dadosPessoais1Service, 'addDadosPessoais1ToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ caracteristicasPsiquicas });
      comp.ngOnInit();

      expect(dadosPessoais1Service.query).toHaveBeenCalled();
      expect(dadosPessoais1Service.addDadosPessoais1ToCollectionIfMissing).toHaveBeenCalledWith(
        dadosPessoais1Collection,
        ...additionalDadosPessoais1s
      );
      expect(comp.dadosPessoais1sSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const caracteristicasPsiquicas: ICaracteristicasPsiquicas = { id: 456 };
      const dadosPessoais1: IDadosPessoais1 = { id: 53708 };
      caracteristicasPsiquicas.dadosPessoais1 = dadosPessoais1;

      activatedRoute.data = of({ caracteristicasPsiquicas });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(caracteristicasPsiquicas));
      expect(comp.dadosPessoais1sSharedCollection).toContain(dadosPessoais1);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CaracteristicasPsiquicas>>();
      const caracteristicasPsiquicas = { id: 123 };
      jest.spyOn(caracteristicasPsiquicasService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ caracteristicasPsiquicas });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: caracteristicasPsiquicas }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(caracteristicasPsiquicasService.update).toHaveBeenCalledWith(caracteristicasPsiquicas);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CaracteristicasPsiquicas>>();
      const caracteristicasPsiquicas = new CaracteristicasPsiquicas();
      jest.spyOn(caracteristicasPsiquicasService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ caracteristicasPsiquicas });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: caracteristicasPsiquicas }));
      saveSubject.complete();

      // THEN
      expect(caracteristicasPsiquicasService.create).toHaveBeenCalledWith(caracteristicasPsiquicas);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CaracteristicasPsiquicas>>();
      const caracteristicasPsiquicas = { id: 123 };
      jest.spyOn(caracteristicasPsiquicasService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ caracteristicasPsiquicas });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(caracteristicasPsiquicasService.update).toHaveBeenCalledWith(caracteristicasPsiquicas);
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
