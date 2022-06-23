import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FotoDocumentoService } from '../service/foto-documento.service';
import { IFotoDocumento, FotoDocumento } from '../foto-documento.model';
import { IDocumento } from 'app/entities/user/documento/documento.model';
import { DocumentoService } from 'app/entities/user/documento/service/documento.service';

import { FotoDocumentoUpdateComponent } from './foto-documento-update.component';

describe('FotoDocumento Management Update Component', () => {
  let comp: FotoDocumentoUpdateComponent;
  let fixture: ComponentFixture<FotoDocumentoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fotoDocumentoService: FotoDocumentoService;
  let documentoService: DocumentoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FotoDocumentoUpdateComponent],
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
      .overrideTemplate(FotoDocumentoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FotoDocumentoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fotoDocumentoService = TestBed.inject(FotoDocumentoService);
    documentoService = TestBed.inject(DocumentoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Documento query and add missing value', () => {
      const fotoDocumento: IFotoDocumento = { id: 456 };
      const documento: IDocumento = { id: 57543 };
      fotoDocumento.documento = documento;

      const documentoCollection: IDocumento[] = [{ id: 90224 }];
      jest.spyOn(documentoService, 'query').mockReturnValue(of(new HttpResponse({ body: documentoCollection })));
      const additionalDocumentos = [documento];
      const expectedCollection: IDocumento[] = [...additionalDocumentos, ...documentoCollection];
      jest.spyOn(documentoService, 'addDocumentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fotoDocumento });
      comp.ngOnInit();

      expect(documentoService.query).toHaveBeenCalled();
      expect(documentoService.addDocumentoToCollectionIfMissing).toHaveBeenCalledWith(documentoCollection, ...additionalDocumentos);
      expect(comp.documentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const fotoDocumento: IFotoDocumento = { id: 456 };
      const documento: IDocumento = { id: 37586 };
      fotoDocumento.documento = documento;

      activatedRoute.data = of({ fotoDocumento });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(fotoDocumento));
      expect(comp.documentosSharedCollection).toContain(documento);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FotoDocumento>>();
      const fotoDocumento = { id: 123 };
      jest.spyOn(fotoDocumentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoDocumento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fotoDocumento }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(fotoDocumentoService.update).toHaveBeenCalledWith(fotoDocumento);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FotoDocumento>>();
      const fotoDocumento = new FotoDocumento();
      jest.spyOn(fotoDocumentoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoDocumento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fotoDocumento }));
      saveSubject.complete();

      // THEN
      expect(fotoDocumentoService.create).toHaveBeenCalledWith(fotoDocumento);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FotoDocumento>>();
      const fotoDocumento = { id: 123 };
      jest.spyOn(fotoDocumentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoDocumento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fotoDocumentoService.update).toHaveBeenCalledWith(fotoDocumento);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackDocumentoById', () => {
      it('Should return tracked Documento primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDocumentoById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
