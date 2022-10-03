import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { StatusContaReceber } from 'app/entities/enumerations/status-conta-receber.model';
import { IReceber, Receber } from '../receber.model';

import { ReceberService } from './receber.service';

describe('Receber Service', () => {
  let service: ReceberService;
  let httpMock: HttpTestingController;
  let elemDefault: IReceber;
  let expectedResult: IReceber | IReceber[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ReceberService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      data: currentDate,
      valor: 0,
      status: StatusContaReceber.VENCIDA,
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

    it('should create a Receber', () => {
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

      service.create(new Receber()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Receber', () => {
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

    it('should partial update a Receber', () => {
      const patchObject = Object.assign(
        {
          data: currentDate.format(DATE_FORMAT),
          valor: 1,
        },
        new Receber()
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

    it('should return a list of Receber', () => {
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

    it('should delete a Receber', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addReceberToCollectionIfMissing', () => {
      it('should add a Receber to an empty array', () => {
        const receber: IReceber = { id: 123 };
        expectedResult = service.addReceberToCollectionIfMissing([], receber);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(receber);
      });

      it('should not add a Receber to an array that contains it', () => {
        const receber: IReceber = { id: 123 };
        const receberCollection: IReceber[] = [
          {
            ...receber,
          },
          { id: 456 },
        ];
        expectedResult = service.addReceberToCollectionIfMissing(receberCollection, receber);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Receber to an array that doesn't contain it", () => {
        const receber: IReceber = { id: 123 };
        const receberCollection: IReceber[] = [{ id: 456 }];
        expectedResult = service.addReceberToCollectionIfMissing(receberCollection, receber);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(receber);
      });

      it('should add only unique Receber to an array', () => {
        const receberArray: IReceber[] = [{ id: 123 }, { id: 456 }, { id: 13693 }];
        const receberCollection: IReceber[] = [{ id: 123 }];
        expectedResult = service.addReceberToCollectionIfMissing(receberCollection, ...receberArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const receber: IReceber = { id: 123 };
        const receber2: IReceber = { id: 456 };
        expectedResult = service.addReceberToCollectionIfMissing([], receber, receber2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(receber);
        expect(expectedResult).toContain(receber2);
      });

      it('should accept null and undefined values', () => {
        const receber: IReceber = { id: 123 };
        expectedResult = service.addReceberToCollectionIfMissing([], null, receber, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(receber);
      });

      it('should return initial array if no Receber is added', () => {
        const receberCollection: IReceber[] = [{ id: 123 }];
        expectedResult = service.addReceberToCollectionIfMissing(receberCollection, undefined, null);
        expect(expectedResult).toEqual(receberCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
