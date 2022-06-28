import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFotoDocumento, FotoDocumento } from '../foto-documento.model';

import { FotoDocumentoService } from './foto-documento.service';

describe('FotoDocumento Service', () => {
  let service: FotoDocumentoService;
  let httpMock: HttpTestingController;
  let elemDefault: IFotoDocumento;
  let expectedResult: IFotoDocumento | IFotoDocumento[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FotoDocumentoService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      conteudoContentType: 'image/png',
      conteudo: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a FotoDocumento', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new FotoDocumento()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FotoDocumento', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          conteudo: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FotoDocumento', () => {
      const patchObject = Object.assign({}, new FotoDocumento());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FotoDocumento', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          conteudo: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a FotoDocumento', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addFotoDocumentoToCollectionIfMissing', () => {
      it('should add a FotoDocumento to an empty array', () => {
        const fotoDocumento: IFotoDocumento = { id: 123 };
        expectedResult = service.addFotoDocumentoToCollectionIfMissing([], fotoDocumento);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fotoDocumento);
      });

      it('should not add a FotoDocumento to an array that contains it', () => {
        const fotoDocumento: IFotoDocumento = { id: 123 };
        const fotoDocumentoCollection: IFotoDocumento[] = [
          {
            ...fotoDocumento,
          },
          { id: 456 },
        ];
        expectedResult = service.addFotoDocumentoToCollectionIfMissing(fotoDocumentoCollection, fotoDocumento);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FotoDocumento to an array that doesn't contain it", () => {
        const fotoDocumento: IFotoDocumento = { id: 123 };
        const fotoDocumentoCollection: IFotoDocumento[] = [{ id: 456 }];
        expectedResult = service.addFotoDocumentoToCollectionIfMissing(fotoDocumentoCollection, fotoDocumento);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fotoDocumento);
      });

      it('should add only unique FotoDocumento to an array', () => {
        const fotoDocumentoArray: IFotoDocumento[] = [{ id: 123 }, { id: 456 }, { id: 57386 }];
        const fotoDocumentoCollection: IFotoDocumento[] = [{ id: 123 }];
        expectedResult = service.addFotoDocumentoToCollectionIfMissing(fotoDocumentoCollection, ...fotoDocumentoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fotoDocumento: IFotoDocumento = { id: 123 };
        const fotoDocumento2: IFotoDocumento = { id: 456 };
        expectedResult = service.addFotoDocumentoToCollectionIfMissing([], fotoDocumento, fotoDocumento2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fotoDocumento);
        expect(expectedResult).toContain(fotoDocumento2);
      });

      it('should accept null and undefined values', () => {
        const fotoDocumento: IFotoDocumento = { id: 123 };
        expectedResult = service.addFotoDocumentoToCollectionIfMissing([], null, fotoDocumento, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fotoDocumento);
      });

      it('should return initial array if no FotoDocumento is added', () => {
        const fotoDocumentoCollection: IFotoDocumento[] = [{ id: 123 }];
        expectedResult = service.addFotoDocumentoToCollectionIfMissing(fotoDocumentoCollection, undefined, null);
        expect(expectedResult).toEqual(fotoDocumentoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
