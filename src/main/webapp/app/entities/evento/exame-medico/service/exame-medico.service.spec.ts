import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IExameMedico, ExameMedico } from '../exame-medico.model';

import { ExameMedicoService } from './exame-medico.service';

describe('ExameMedico Service', () => {
  let service: ExameMedicoService;
  let httpMock: HttpTestingController;
  let elemDefault: IExameMedico;
  let expectedResult: IExameMedico | IExameMedico[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ExameMedicoService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      data: currentDate,
      nomeMedico: 'AAAAAAA',
      crmMedico: 'AAAAAAA',
      resumo: 'AAAAAAA',
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

    it('should create a ExameMedico', () => {
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

      service.create(new ExameMedico()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ExameMedico', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          data: currentDate.format(DATE_FORMAT),
          nomeMedico: 'BBBBBB',
          crmMedico: 'BBBBBB',
          resumo: 'BBBBBB',
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

    it('should partial update a ExameMedico', () => {
      const patchObject = Object.assign(
        {
          nomeMedico: 'BBBBBB',
          obs: 'BBBBBB',
        },
        new ExameMedico()
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

    it('should return a list of ExameMedico', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          data: currentDate.format(DATE_FORMAT),
          nomeMedico: 'BBBBBB',
          crmMedico: 'BBBBBB',
          resumo: 'BBBBBB',
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

    it('should delete a ExameMedico', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addExameMedicoToCollectionIfMissing', () => {
      it('should add a ExameMedico to an empty array', () => {
        const exameMedico: IExameMedico = { id: 123 };
        expectedResult = service.addExameMedicoToCollectionIfMissing([], exameMedico);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(exameMedico);
      });

      it('should not add a ExameMedico to an array that contains it', () => {
        const exameMedico: IExameMedico = { id: 123 };
        const exameMedicoCollection: IExameMedico[] = [
          {
            ...exameMedico,
          },
          { id: 456 },
        ];
        expectedResult = service.addExameMedicoToCollectionIfMissing(exameMedicoCollection, exameMedico);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ExameMedico to an array that doesn't contain it", () => {
        const exameMedico: IExameMedico = { id: 123 };
        const exameMedicoCollection: IExameMedico[] = [{ id: 456 }];
        expectedResult = service.addExameMedicoToCollectionIfMissing(exameMedicoCollection, exameMedico);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(exameMedico);
      });

      it('should add only unique ExameMedico to an array', () => {
        const exameMedicoArray: IExameMedico[] = [{ id: 123 }, { id: 456 }, { id: 71181 }];
        const exameMedicoCollection: IExameMedico[] = [{ id: 123 }];
        expectedResult = service.addExameMedicoToCollectionIfMissing(exameMedicoCollection, ...exameMedicoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const exameMedico: IExameMedico = { id: 123 };
        const exameMedico2: IExameMedico = { id: 456 };
        expectedResult = service.addExameMedicoToCollectionIfMissing([], exameMedico, exameMedico2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(exameMedico);
        expect(expectedResult).toContain(exameMedico2);
      });

      it('should accept null and undefined values', () => {
        const exameMedico: IExameMedico = { id: 123 };
        expectedResult = service.addExameMedicoToCollectionIfMissing([], null, exameMedico, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(exameMedico);
      });

      it('should return initial array if no ExameMedico is added', () => {
        const exameMedicoCollection: IExameMedico[] = [{ id: 123 }];
        expectedResult = service.addExameMedicoToCollectionIfMissing(exameMedicoCollection, undefined, null);
        expect(expectedResult).toEqual(exameMedicoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
