import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IEntradaEstoque, EntradaEstoque } from '../entrada-estoque.model';

import { EntradaEstoqueService } from './entrada-estoque.service';

describe('EntradaEstoque Service', () => {
  let service: EntradaEstoqueService;
  let httpMock: HttpTestingController;
  let elemDefault: IEntradaEstoque;
  let expectedResult: IEntradaEstoque | IEntradaEstoque[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EntradaEstoqueService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      data: currentDate,
      qtde: 0,
      valorUnitario: 0,
      obs: 'AAAAAAA',
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

    it('should create a EntradaEstoque', () => {
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

      service.create(new EntradaEstoque()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EntradaEstoque', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          data: currentDate.format(DATE_FORMAT),
          qtde: 1,
          valorUnitario: 1,
          obs: 'BBBBBB',
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

    it('should partial update a EntradaEstoque', () => {
      const patchObject = Object.assign(
        {
          qtde: 1,
          valorUnitario: 1,
          obs: 'BBBBBB',
        },
        new EntradaEstoque()
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

    it('should return a list of EntradaEstoque', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          data: currentDate.format(DATE_FORMAT),
          qtde: 1,
          valorUnitario: 1,
          obs: 'BBBBBB',
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

    it('should delete a EntradaEstoque', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addEntradaEstoqueToCollectionIfMissing', () => {
      it('should add a EntradaEstoque to an empty array', () => {
        const entradaEstoque: IEntradaEstoque = { id: 123 };
        expectedResult = service.addEntradaEstoqueToCollectionIfMissing([], entradaEstoque);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(entradaEstoque);
      });

      it('should not add a EntradaEstoque to an array that contains it', () => {
        const entradaEstoque: IEntradaEstoque = { id: 123 };
        const entradaEstoqueCollection: IEntradaEstoque[] = [
          {
            ...entradaEstoque,
          },
          { id: 456 },
        ];
        expectedResult = service.addEntradaEstoqueToCollectionIfMissing(entradaEstoqueCollection, entradaEstoque);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EntradaEstoque to an array that doesn't contain it", () => {
        const entradaEstoque: IEntradaEstoque = { id: 123 };
        const entradaEstoqueCollection: IEntradaEstoque[] = [{ id: 456 }];
        expectedResult = service.addEntradaEstoqueToCollectionIfMissing(entradaEstoqueCollection, entradaEstoque);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(entradaEstoque);
      });

      it('should add only unique EntradaEstoque to an array', () => {
        const entradaEstoqueArray: IEntradaEstoque[] = [{ id: 123 }, { id: 456 }, { id: 88338 }];
        const entradaEstoqueCollection: IEntradaEstoque[] = [{ id: 123 }];
        expectedResult = service.addEntradaEstoqueToCollectionIfMissing(entradaEstoqueCollection, ...entradaEstoqueArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const entradaEstoque: IEntradaEstoque = { id: 123 };
        const entradaEstoque2: IEntradaEstoque = { id: 456 };
        expectedResult = service.addEntradaEstoqueToCollectionIfMissing([], entradaEstoque, entradaEstoque2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(entradaEstoque);
        expect(expectedResult).toContain(entradaEstoque2);
      });

      it('should accept null and undefined values', () => {
        const entradaEstoque: IEntradaEstoque = { id: 123 };
        expectedResult = service.addEntradaEstoqueToCollectionIfMissing([], null, entradaEstoque, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(entradaEstoque);
      });

      it('should return initial array if no EntradaEstoque is added', () => {
        const entradaEstoqueCollection: IEntradaEstoque[] = [{ id: 123 }];
        expectedResult = service.addEntradaEstoqueToCollectionIfMissing(entradaEstoqueCollection, undefined, null);
        expect(expectedResult).toEqual(entradaEstoqueCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
