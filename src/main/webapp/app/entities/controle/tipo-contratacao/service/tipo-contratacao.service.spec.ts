import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITipoContratacao, TipoContratacao } from '../tipo-contratacao.model';

import { TipoContratacaoService } from './tipo-contratacao.service';

describe('TipoContratacao Service', () => {
  let service: TipoContratacaoService;
  let httpMock: HttpTestingController;
  let elemDefault: ITipoContratacao;
  let expectedResult: ITipoContratacao | ITipoContratacao[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TipoContratacaoService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nome: 'AAAAAAA',
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

    it('should create a TipoContratacao', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new TipoContratacao()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TipoContratacao', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nome: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TipoContratacao', () => {
      const patchObject = Object.assign({}, new TipoContratacao());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TipoContratacao', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nome: 'BBBBBB',
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

    it('should delete a TipoContratacao', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTipoContratacaoToCollectionIfMissing', () => {
      it('should add a TipoContratacao to an empty array', () => {
        const tipoContratacao: ITipoContratacao = { id: 123 };
        expectedResult = service.addTipoContratacaoToCollectionIfMissing([], tipoContratacao);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tipoContratacao);
      });

      it('should not add a TipoContratacao to an array that contains it', () => {
        const tipoContratacao: ITipoContratacao = { id: 123 };
        const tipoContratacaoCollection: ITipoContratacao[] = [
          {
            ...tipoContratacao,
          },
          { id: 456 },
        ];
        expectedResult = service.addTipoContratacaoToCollectionIfMissing(tipoContratacaoCollection, tipoContratacao);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TipoContratacao to an array that doesn't contain it", () => {
        const tipoContratacao: ITipoContratacao = { id: 123 };
        const tipoContratacaoCollection: ITipoContratacao[] = [{ id: 456 }];
        expectedResult = service.addTipoContratacaoToCollectionIfMissing(tipoContratacaoCollection, tipoContratacao);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tipoContratacao);
      });

      it('should add only unique TipoContratacao to an array', () => {
        const tipoContratacaoArray: ITipoContratacao[] = [{ id: 123 }, { id: 456 }, { id: 6788 }];
        const tipoContratacaoCollection: ITipoContratacao[] = [{ id: 123 }];
        expectedResult = service.addTipoContratacaoToCollectionIfMissing(tipoContratacaoCollection, ...tipoContratacaoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tipoContratacao: ITipoContratacao = { id: 123 };
        const tipoContratacao2: ITipoContratacao = { id: 456 };
        expectedResult = service.addTipoContratacaoToCollectionIfMissing([], tipoContratacao, tipoContratacao2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tipoContratacao);
        expect(expectedResult).toContain(tipoContratacao2);
      });

      it('should accept null and undefined values', () => {
        const tipoContratacao: ITipoContratacao = { id: 123 };
        expectedResult = service.addTipoContratacaoToCollectionIfMissing([], null, tipoContratacao, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tipoContratacao);
      });

      it('should return initial array if no TipoContratacao is added', () => {
        const tipoContratacaoCollection: ITipoContratacao[] = [{ id: 123 }];
        expectedResult = service.addTipoContratacaoToCollectionIfMissing(tipoContratacaoCollection, undefined, null);
        expect(expectedResult).toEqual(tipoContratacaoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
