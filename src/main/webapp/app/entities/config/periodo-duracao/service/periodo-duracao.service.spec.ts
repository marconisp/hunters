import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IPeriodoDuracao, PeriodoDuracao } from '../periodo-duracao.model';

import { PeriodoDuracaoService } from './periodo-duracao.service';

describe('PeriodoDuracao Service', () => {
  let service: PeriodoDuracaoService;
  let httpMock: HttpTestingController;
  let elemDefault: IPeriodoDuracao;
  let expectedResult: IPeriodoDuracao | IPeriodoDuracao[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PeriodoDuracaoService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      nome: 'AAAAAAA',
      dataInicio: currentDate,
      dataFim: currentDate,
      horaInicio: 'AAAAAAA',
      horaFim: 'AAAAAAA',
      obs: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dataInicio: currentDate.format(DATE_FORMAT),
          dataFim: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a PeriodoDuracao', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dataInicio: currentDate.format(DATE_FORMAT),
          dataFim: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dataInicio: currentDate,
          dataFim: currentDate,
        },
        returnedFromService
      );

      service.create(new PeriodoDuracao()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PeriodoDuracao', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nome: 'BBBBBB',
          dataInicio: currentDate.format(DATE_FORMAT),
          dataFim: currentDate.format(DATE_FORMAT),
          horaInicio: 'BBBBBB',
          horaFim: 'BBBBBB',
          obs: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dataInicio: currentDate,
          dataFim: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PeriodoDuracao', () => {
      const patchObject = Object.assign(
        {
          dataInicio: currentDate.format(DATE_FORMAT),
          horaFim: 'BBBBBB',
        },
        new PeriodoDuracao()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dataInicio: currentDate,
          dataFim: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PeriodoDuracao', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nome: 'BBBBBB',
          dataInicio: currentDate.format(DATE_FORMAT),
          dataFim: currentDate.format(DATE_FORMAT),
          horaInicio: 'BBBBBB',
          horaFim: 'BBBBBB',
          obs: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dataInicio: currentDate,
          dataFim: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a PeriodoDuracao', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPeriodoDuracaoToCollectionIfMissing', () => {
      it('should add a PeriodoDuracao to an empty array', () => {
        const periodoDuracao: IPeriodoDuracao = { id: 123 };
        expectedResult = service.addPeriodoDuracaoToCollectionIfMissing([], periodoDuracao);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(periodoDuracao);
      });

      it('should not add a PeriodoDuracao to an array that contains it', () => {
        const periodoDuracao: IPeriodoDuracao = { id: 123 };
        const periodoDuracaoCollection: IPeriodoDuracao[] = [
          {
            ...periodoDuracao,
          },
          { id: 456 },
        ];
        expectedResult = service.addPeriodoDuracaoToCollectionIfMissing(periodoDuracaoCollection, periodoDuracao);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PeriodoDuracao to an array that doesn't contain it", () => {
        const periodoDuracao: IPeriodoDuracao = { id: 123 };
        const periodoDuracaoCollection: IPeriodoDuracao[] = [{ id: 456 }];
        expectedResult = service.addPeriodoDuracaoToCollectionIfMissing(periodoDuracaoCollection, periodoDuracao);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(periodoDuracao);
      });

      it('should add only unique PeriodoDuracao to an array', () => {
        const periodoDuracaoArray: IPeriodoDuracao[] = [{ id: 123 }, { id: 456 }, { id: 6875 }];
        const periodoDuracaoCollection: IPeriodoDuracao[] = [{ id: 123 }];
        expectedResult = service.addPeriodoDuracaoToCollectionIfMissing(periodoDuracaoCollection, ...periodoDuracaoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const periodoDuracao: IPeriodoDuracao = { id: 123 };
        const periodoDuracao2: IPeriodoDuracao = { id: 456 };
        expectedResult = service.addPeriodoDuracaoToCollectionIfMissing([], periodoDuracao, periodoDuracao2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(periodoDuracao);
        expect(expectedResult).toContain(periodoDuracao2);
      });

      it('should accept null and undefined values', () => {
        const periodoDuracao: IPeriodoDuracao = { id: 123 };
        expectedResult = service.addPeriodoDuracaoToCollectionIfMissing([], null, periodoDuracao, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(periodoDuracao);
      });

      it('should return initial array if no PeriodoDuracao is added', () => {
        const periodoDuracaoCollection: IPeriodoDuracao[] = [{ id: 123 }];
        expectedResult = service.addPeriodoDuracaoToCollectionIfMissing(periodoDuracaoCollection, undefined, null);
        expect(expectedResult).toEqual(periodoDuracaoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
