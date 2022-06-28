import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IAviso, Aviso } from '../aviso.model';

import { AvisoService } from './aviso.service';

describe('Aviso Service', () => {
  let service: AvisoService;
  let httpMock: HttpTestingController;
  let elemDefault: IAviso;
  let expectedResult: IAviso | IAviso[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AvisoService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      data: currentDate,
      titulo: 'AAAAAAA',
      conteudo: 'AAAAAAA',
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

    it('should create a Aviso', () => {
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

      service.create(new Aviso()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Aviso', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          data: currentDate.format(DATE_FORMAT),
          titulo: 'BBBBBB',
          conteudo: 'BBBBBB',
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

    it('should partial update a Aviso', () => {
      const patchObject = Object.assign(
        {
          data: currentDate.format(DATE_FORMAT),
        },
        new Aviso()
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

    it('should return a list of Aviso', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          data: currentDate.format(DATE_FORMAT),
          titulo: 'BBBBBB',
          conteudo: 'BBBBBB',
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

    it('should delete a Aviso', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAvisoToCollectionIfMissing', () => {
      it('should add a Aviso to an empty array', () => {
        const aviso: IAviso = { id: 123 };
        expectedResult = service.addAvisoToCollectionIfMissing([], aviso);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(aviso);
      });

      it('should not add a Aviso to an array that contains it', () => {
        const aviso: IAviso = { id: 123 };
        const avisoCollection: IAviso[] = [
          {
            ...aviso,
          },
          { id: 456 },
        ];
        expectedResult = service.addAvisoToCollectionIfMissing(avisoCollection, aviso);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Aviso to an array that doesn't contain it", () => {
        const aviso: IAviso = { id: 123 };
        const avisoCollection: IAviso[] = [{ id: 456 }];
        expectedResult = service.addAvisoToCollectionIfMissing(avisoCollection, aviso);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(aviso);
      });

      it('should add only unique Aviso to an array', () => {
        const avisoArray: IAviso[] = [{ id: 123 }, { id: 456 }, { id: 9044 }];
        const avisoCollection: IAviso[] = [{ id: 123 }];
        expectedResult = service.addAvisoToCollectionIfMissing(avisoCollection, ...avisoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const aviso: IAviso = { id: 123 };
        const aviso2: IAviso = { id: 456 };
        expectedResult = service.addAvisoToCollectionIfMissing([], aviso, aviso2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(aviso);
        expect(expectedResult).toContain(aviso2);
      });

      it('should accept null and undefined values', () => {
        const aviso: IAviso = { id: 123 };
        expectedResult = service.addAvisoToCollectionIfMissing([], null, aviso, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(aviso);
      });

      it('should return initial array if no Aviso is added', () => {
        const avisoCollection: IAviso[] = [{ id: 123 }];
        expectedResult = service.addAvisoToCollectionIfMissing(avisoCollection, undefined, null);
        expect(expectedResult).toEqual(avisoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
