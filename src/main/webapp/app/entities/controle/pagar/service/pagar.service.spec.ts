import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { StatusContaPagar } from 'app/entities/enumerations/status-conta-pagar.model';
import { IPagar, Pagar } from '../pagar.model';

import { PagarService } from './pagar.service';

describe('Pagar Service', () => {
  let service: PagarService;
  let httpMock: HttpTestingController;
  let elemDefault: IPagar;
  let expectedResult: IPagar | IPagar[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PagarService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      data: currentDate,
      valor: 0,
      status: StatusContaPagar.VENCIDA,
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

    it('should create a Pagar', () => {
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

      service.create(new Pagar()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Pagar', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          data: currentDate.format(DATE_FORMAT),
          valor: 1,
          status: 'BBBBBB',
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

    it('should partial update a Pagar', () => {
      const patchObject = Object.assign(
        {
          data: currentDate.format(DATE_FORMAT),
          valor: 1,
          status: 'BBBBBB',
          obs: 'BBBBBB',
        },
        new Pagar()
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

    it('should return a list of Pagar', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          data: currentDate.format(DATE_FORMAT),
          valor: 1,
          status: 'BBBBBB',
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

    it('should delete a Pagar', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPagarToCollectionIfMissing', () => {
      it('should add a Pagar to an empty array', () => {
        const pagar: IPagar = { id: 123 };
        expectedResult = service.addPagarToCollectionIfMissing([], pagar);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pagar);
      });

      it('should not add a Pagar to an array that contains it', () => {
        const pagar: IPagar = { id: 123 };
        const pagarCollection: IPagar[] = [
          {
            ...pagar,
          },
          { id: 456 },
        ];
        expectedResult = service.addPagarToCollectionIfMissing(pagarCollection, pagar);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Pagar to an array that doesn't contain it", () => {
        const pagar: IPagar = { id: 123 };
        const pagarCollection: IPagar[] = [{ id: 456 }];
        expectedResult = service.addPagarToCollectionIfMissing(pagarCollection, pagar);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pagar);
      });

      it('should add only unique Pagar to an array', () => {
        const pagarArray: IPagar[] = [{ id: 123 }, { id: 456 }, { id: 56529 }];
        const pagarCollection: IPagar[] = [{ id: 123 }];
        expectedResult = service.addPagarToCollectionIfMissing(pagarCollection, ...pagarArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pagar: IPagar = { id: 123 };
        const pagar2: IPagar = { id: 456 };
        expectedResult = service.addPagarToCollectionIfMissing([], pagar, pagar2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pagar);
        expect(expectedResult).toContain(pagar2);
      });

      it('should accept null and undefined values', () => {
        const pagar: IPagar = { id: 123 };
        expectedResult = service.addPagarToCollectionIfMissing([], null, pagar, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pagar);
      });

      it('should return initial array if no Pagar is added', () => {
        const pagarCollection: IPagar[] = [{ id: 123 }];
        expectedResult = service.addPagarToCollectionIfMissing(pagarCollection, undefined, null);
        expect(expectedResult).toEqual(pagarCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
