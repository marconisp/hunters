import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DocumentoService } from '../service/documento.service';
import { IDocumento, Documento } from '../documento.model';
import { ITipoDocumento } from 'app/entities/config/tipo-documento/tipo-documento.model';
import { TipoDocumentoService } from 'app/entities/config/tipo-documento/service/tipo-documento.service';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';
import { DadosPessoaisService } from 'app/entities/user/dados-pessoais/service/dados-pessoais.service';

import { DocumentoUpdateComponent } from './documento-update.component';

describe('Documento Management Update Component', () => {
  let comp: DocumentoUpdateComponent;
  let fixture: ComponentFixture<DocumentoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let documentoService: DocumentoService;
  let tipoDocumentoService: TipoDocumentoService;
  let dadosPessoaisService: DadosPessoaisService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DocumentoUpdateComponent],
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
      .overrideTemplate(DocumentoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DocumentoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    documentoService = TestBed.inject(DocumentoService);
    tipoDocumentoService = TestBed.inject(TipoDocumentoService);
    dadosPessoaisService = TestBed.inject(DadosPessoaisService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call tipoDocumento query and add missing value', () => {
      const documento: IDocumento = { id: 456 };
      const tipoDocumento: ITipoDocumento = { id: 36411 };
      documento.tipoDocumento = tipoDocumento;

      const tipoDocumentoCollection: ITipoDocumento[] = [{ id: 80877 }];
      jest.spyOn(tipoDocumentoService, 'query').mockReturnValue(of(new HttpResponse({ body: tipoDocumentoCollection })));
      const expectedCollection: ITipoDocumento[] = [tipoDocumento, ...tipoDocumentoCollection];
      jest.spyOn(tipoDocumentoService, 'addTipoDocumentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ documento });
      comp.ngOnInit();

      expect(tipoDocumentoService.query).toHaveBeenCalled();
      expect(tipoDocumentoService.addTipoDocumentoToCollectionIfMissing).toHaveBeenCalledWith(tipoDocumentoCollection, tipoDocumento);
      expect(comp.tipoDocumentosCollection).toEqual(expectedCollection);
    });

    it('Should call DadosPessoais query and add missing value', () => {
      const documento: IDocumento = { id: 456 };
      const dadosPessoais: IDadosPessoais = { id: 90485 };
      documento.dadosPessoais = dadosPessoais;

      const dadosPessoaisCollection: IDadosPessoais[] = [{ id: 10342 }];
      jest.spyOn(dadosPessoaisService, 'query').mockReturnValue(of(new HttpResponse({ body: dadosPessoaisCollection })));
      const additionalDadosPessoais = [dadosPessoais];
      const expectedCollection: IDadosPessoais[] = [...additionalDadosPessoais, ...dadosPessoaisCollection];
      jest.spyOn(dadosPessoaisService, 'addDadosPessoaisToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ documento });
      comp.ngOnInit();

      expect(dadosPessoaisService.query).toHaveBeenCalled();
      expect(dadosPessoaisService.addDadosPessoaisToCollectionIfMissing).toHaveBeenCalledWith(
        dadosPessoaisCollection,
        ...additionalDadosPessoais
      );
      expect(comp.dadosPessoaisSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const documento: IDocumento = { id: 456 };
      const tipoDocumento: ITipoDocumento = { id: 4819 };
      documento.tipoDocumento = tipoDocumento;
      const dadosPessoais: IDadosPessoais = { id: 50451 };
      documento.dadosPessoais = dadosPessoais;

      activatedRoute.data = of({ documento });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(documento));
      expect(comp.tipoDocumentosCollection).toContain(tipoDocumento);
      expect(comp.dadosPessoaisSharedCollection).toContain(dadosPessoais);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Documento>>();
      const documento = { id: 123 };
      jest.spyOn(documentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: documento }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(documentoService.update).toHaveBeenCalledWith(documento);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Documento>>();
      const documento = new Documento();
      jest.spyOn(documentoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: documento }));
      saveSubject.complete();

      // THEN
      expect(documentoService.create).toHaveBeenCalledWith(documento);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Documento>>();
      const documento = { id: 123 };
      jest.spyOn(documentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(documentoService.update).toHaveBeenCalledWith(documento);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackTipoDocumentoById', () => {
      it('Should return tracked TipoDocumento primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTipoDocumentoById(0, entity);
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
