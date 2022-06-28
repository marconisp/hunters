import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AvisoService } from '../service/aviso.service';
import { IAviso, Aviso } from '../aviso.model';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';
import { DadosPessoaisService } from 'app/entities/user/dados-pessoais/service/dados-pessoais.service';

import { AvisoUpdateComponent } from './aviso-update.component';

describe('Aviso Management Update Component', () => {
  let comp: AvisoUpdateComponent;
  let fixture: ComponentFixture<AvisoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let avisoService: AvisoService;
  let dadosPessoaisService: DadosPessoaisService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AvisoUpdateComponent],
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
      .overrideTemplate(AvisoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AvisoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    avisoService = TestBed.inject(AvisoService);
    dadosPessoaisService = TestBed.inject(DadosPessoaisService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DadosPessoais query and add missing value', () => {
      const aviso: IAviso = { id: 456 };
      const dadosPessoais: IDadosPessoais = { id: 64631 };
      aviso.dadosPessoais = dadosPessoais;

      const dadosPessoaisCollection: IDadosPessoais[] = [{ id: 8696 }];
      jest.spyOn(dadosPessoaisService, 'query').mockReturnValue(of(new HttpResponse({ body: dadosPessoaisCollection })));
      const additionalDadosPessoais = [dadosPessoais];
      const expectedCollection: IDadosPessoais[] = [...additionalDadosPessoais, ...dadosPessoaisCollection];
      jest.spyOn(dadosPessoaisService, 'addDadosPessoaisToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ aviso });
      comp.ngOnInit();

      expect(dadosPessoaisService.query).toHaveBeenCalled();
      expect(dadosPessoaisService.addDadosPessoaisToCollectionIfMissing).toHaveBeenCalledWith(
        dadosPessoaisCollection,
        ...additionalDadosPessoais
      );
      expect(comp.dadosPessoaisSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const aviso: IAviso = { id: 456 };
      const dadosPessoais: IDadosPessoais = { id: 82182 };
      aviso.dadosPessoais = dadosPessoais;

      activatedRoute.data = of({ aviso });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(aviso));
      expect(comp.dadosPessoaisSharedCollection).toContain(dadosPessoais);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Aviso>>();
      const aviso = { id: 123 };
      jest.spyOn(avisoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ aviso });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: aviso }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(avisoService.update).toHaveBeenCalledWith(aviso);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Aviso>>();
      const aviso = new Aviso();
      jest.spyOn(avisoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ aviso });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: aviso }));
      saveSubject.complete();

      // THEN
      expect(avisoService.create).toHaveBeenCalledWith(aviso);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Aviso>>();
      const aviso = { id: 123 };
      jest.spyOn(avisoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ aviso });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(avisoService.update).toHaveBeenCalledWith(aviso);
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
