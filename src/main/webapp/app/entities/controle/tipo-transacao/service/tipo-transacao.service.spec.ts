import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITipoTransacao, TipoTransacao } from '../tipo-transacao.model';

import { TipoTransacaoService } from './tipo-transacao.service';

describe('TipoTransacao Service', () => {
  let service: TipoTransacaoService;
  let httpMock: HttpTestingController;
  let elemDefault: ITipoTransacao;
  let expectedResult: ITipoTransacao | ITipoTransacao[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TipoTransacaoService);
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

    it('should create a TipoTransacao', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new TipoTransacao()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TipoTransacao', () => {
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

    it('should partial update a TipoTransacao', () => {
      const patchObject = Object.assign({}, new TipoTransacao());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TipoTransacao', () => {
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

    it('should delete a TipoTransacao', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTipoTransacaoToCollectionIfMissing', () => {
      it('should add a TipoTransacao to an empty array', () => {
        const tipoTransacao: ITipoTransacao = { id: 123 };
        expectedResult = service.addTipoTransacaoToCollectionIfMissing([], tipoTransacao);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tipoTransacao);
      });

      it('should not add a TipoTransacao to an array that contains it', () => {
        const tipoTransacao: ITipoTransacao = { id: 123 };
        const tipoTransacaoCollection: ITipoTransacao[] = [
          {
            ...tipoTransacao,
          },
          { id: 456 },
        ];
        expectedResult = service.addTipoTransacaoToCollectionIfMissing(tipoTransacaoCollection, tipoTransacao);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TipoTransacao to an array that doesn't contain it", () => {
        const tipoTransacao: ITipoTransacao = { id: 123 };
        const tipoTransacaoCollection: ITipoTransacao[] = [{ id: 456 }];
        expectedResult = service.addTipoTransacaoToCollectionIfMissing(tipoTransacaoCollection, tipoTransacao);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tipoTransacao);
      });

      it('should add only unique TipoTransacao to an array', () => {
        const tipoTransacaoArray: ITipoTransacao[] = [{ id: 123 }, { id: 456 }, { id: 17577 }];
        const tipoTransacaoCollection: ITipoTransacao[] = [{ id: 123 }];
        expectedResult = service.addTipoTransacaoToCollectionIfMissing(tipoTransacaoCollection, ...tipoTransacaoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tipoTransacao: ITipoTransacao = { id: 123 };
        const tipoTransacao2: ITipoTransacao = { id: 456 };
        expectedResult = service.addTipoTransacaoToCollectionIfMissing([], tipoTransacao, tipoTransacao2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tipoTransacao);
        expect(expectedResult).toContain(tipoTransacao2);
      });

      it('should accept null and undefined values', () => {
        const tipoTransacao: ITipoTransacao = { id: 123 };
        expectedResult = service.addTipoTransacaoToCollectionIfMissing([], null, tipoTransacao, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tipoTransacao);
      });

      it('should return initial array if no TipoTransacao is added', () => {
        const tipoTransacaoCollection: ITipoTransacao[] = [{ id: 123 }];
        expectedResult = service.addTipoTransacaoToCollectionIfMissing(tipoTransacaoCollection, undefined, null);
        expect(expectedResult).toEqual(tipoTransacaoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
