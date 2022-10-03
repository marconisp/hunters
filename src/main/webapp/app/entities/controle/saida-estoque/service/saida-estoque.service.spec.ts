import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ISaidaEstoque, SaidaEstoque } from '../saida-estoque.model';

import { SaidaEstoqueService } from './saida-estoque.service';

describe('SaidaEstoque Service', () => {
  let service: SaidaEstoqueService;
  let httpMock: HttpTestingController;
  let elemDefault: ISaidaEstoque;
  let expectedResult: ISaidaEstoque | ISaidaEstoque[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SaidaEstoqueService);
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

    it('should create a SaidaEstoque', () => {
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

      service.create(new SaidaEstoque()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SaidaEstoque', () => {
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

    it('should partial update a SaidaEstoque', () => {
      const patchObject = Object.assign(
        {
          data: currentDate.format(DATE_FORMAT),
        },
        new SaidaEstoque()
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

    it('should return a list of SaidaEstoque', () => {
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

    it('should delete a SaidaEstoque', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSaidaEstoqueToCollectionIfMissing', () => {
      it('should add a SaidaEstoque to an empty array', () => {
        const saidaEstoque: ISaidaEstoque = { id: 123 };
        expectedResult = service.addSaidaEstoqueToCollectionIfMissing([], saidaEstoque);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(saidaEstoque);
      });

      it('should not add a SaidaEstoque to an array that contains it', () => {
        const saidaEstoque: ISaidaEstoque = { id: 123 };
        const saidaEstoqueCollection: ISaidaEstoque[] = [
          {
            ...saidaEstoque,
          },
          { id: 456 },
        ];
        expectedResult = service.addSaidaEstoqueToCollectionIfMissing(saidaEstoqueCollection, saidaEstoque);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SaidaEstoque to an array that doesn't contain it", () => {
        const saidaEstoque: ISaidaEstoque = { id: 123 };
        const saidaEstoqueCollection: ISaidaEstoque[] = [{ id: 456 }];
        expectedResult = service.addSaidaEstoqueToCollectionIfMissing(saidaEstoqueCollection, saidaEstoque);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(saidaEstoque);
      });

      it('should add only unique SaidaEstoque to an array', () => {
        const saidaEstoqueArray: ISaidaEstoque[] = [{ id: 123 }, { id: 456 }, { id: 51986 }];
        const saidaEstoqueCollection: ISaidaEstoque[] = [{ id: 123 }];
        expectedResult = service.addSaidaEstoqueToCollectionIfMissing(saidaEstoqueCollection, ...saidaEstoqueArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const saidaEstoque: ISaidaEstoque = { id: 123 };
        const saidaEstoque2: ISaidaEstoque = { id: 456 };
        expectedResult = service.addSaidaEstoqueToCollectionIfMissing([], saidaEstoque, saidaEstoque2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(saidaEstoque);
        expect(expectedResult).toContain(saidaEstoque2);
      });

      it('should accept null and undefined values', () => {
        const saidaEstoque: ISaidaEstoque = { id: 123 };
        expectedResult = service.addSaidaEstoqueToCollectionIfMissing([], null, saidaEstoque, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(saidaEstoque);
      });

      it('should return initial array if no SaidaEstoque is added', () => {
        const saidaEstoqueCollection: ISaidaEstoque[] = [{ id: 123 }];
        expectedResult = service.addSaidaEstoqueToCollectionIfMissing(saidaEstoqueCollection, undefined, null);
        expect(expectedResult).toEqual(saidaEstoqueCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
