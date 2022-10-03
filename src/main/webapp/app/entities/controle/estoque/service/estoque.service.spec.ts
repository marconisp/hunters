import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IEstoque, Estoque } from '../estoque.model';

import { EstoqueService } from './estoque.service';

describe('Estoque Service', () => {
  let service: EstoqueService;
  let httpMock: HttpTestingController;
  let elemDefault: IEstoque;
  let expectedResult: IEstoque | IEstoque[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EstoqueService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      data: currentDate,
      qtde: 0,
      valorUnitario: 0,
      valorTotal: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          data: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Estoque', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          data: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          data: currentDate,
        },
        returnedFromService
      );

      service.create(new Estoque()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Estoque', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          data: currentDate.format(DATE_FORMAT),
          qtde: 1,
          valorUnitario: 1,
          valorTotal: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          data: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Estoque', () => {
      const patchObject = Object.assign(
        {
          data: currentDate.format(DATE_FORMAT),
          valorUnitario: 1,
          valorTotal: 1,
        },
        new Estoque()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          data: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Estoque', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          data: currentDate.format(DATE_FORMAT),
          qtde: 1,
          valorUnitario: 1,
          valorTotal: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          data: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Estoque', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addEstoqueToCollectionIfMissing', () => {
      it('should add a Estoque to an empty array', () => {
        const estoque: IEstoque = { id: 123 };
        expectedResult = service.addEstoqueToCollectionIfMissing([], estoque);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(estoque);
      });

      it('should not add a Estoque to an array that contains it', () => {
        const estoque: IEstoque = { id: 123 };
        const estoqueCollection: IEstoque[] = [
          {
            ...estoque,
          },
          { id: 456 },
        ];
        expectedResult = service.addEstoqueToCollectionIfMissing(estoqueCollection, estoque);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Estoque to an array that doesn't contain it", () => {
        const estoque: IEstoque = { id: 123 };
        const estoqueCollection: IEstoque[] = [{ id: 456 }];
        expectedResult = service.addEstoqueToCollectionIfMissing(estoqueCollection, estoque);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(estoque);
      });

      it('should add only unique Estoque to an array', () => {
        const estoqueArray: IEstoque[] = [{ id: 123 }, { id: 456 }, { id: 38956 }];
        const estoqueCollection: IEstoque[] = [{ id: 123 }];
        expectedResult = service.addEstoqueToCollectionIfMissing(estoqueCollection, ...estoqueArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const estoque: IEstoque = { id: 123 };
        const estoque2: IEstoque = { id: 456 };
        expectedResult = service.addEstoqueToCollectionIfMissing([], estoque, estoque2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(estoque);
        expect(expectedResult).toContain(estoque2);
      });

      it('should accept null and undefined values', () => {
        const estoque: IEstoque = { id: 123 };
        expectedResult = service.addEstoqueToCollectionIfMissing([], null, estoque, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(estoque);
      });

      it('should return initial array if no Estoque is added', () => {
        const estoqueCollection: IEstoque[] = [{ id: 123 }];
        expectedResult = service.addEstoqueToCollectionIfMissing(estoqueCollection, undefined, null);
        expect(expectedResult).toEqual(estoqueCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
