import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEstadoCivil, EstadoCivil } from '../estado-civil.model';

import { EstadoCivilService } from './estado-civil.service';

describe('EstadoCivil Service', () => {
  let service: EstadoCivilService;
  let httpMock: HttpTestingController;
  let elemDefault: IEstadoCivil;
  let expectedResult: IEstadoCivil | IEstadoCivil[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EstadoCivilService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      codigo: 'AAAAAAA',
      descricao: 'AAAAAAA',
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

    it('should create a EstadoCivil', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new EstadoCivil()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EstadoCivil', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          codigo: 'BBBBBB',
          descricao: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EstadoCivil', () => {
      const patchObject = Object.assign({}, new EstadoCivil());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EstadoCivil', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          codigo: 'BBBBBB',
          descricao: 'BBBBBB',
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

    it('should delete a EstadoCivil', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addEstadoCivilToCollectionIfMissing', () => {
      it('should add a EstadoCivil to an empty array', () => {
        const estadoCivil: IEstadoCivil = { id: 123 };
        expectedResult = service.addEstadoCivilToCollectionIfMissing([], estadoCivil);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(estadoCivil);
      });

      it('should not add a EstadoCivil to an array that contains it', () => {
        const estadoCivil: IEstadoCivil = { id: 123 };
        const estadoCivilCollection: IEstadoCivil[] = [
          {
            ...estadoCivil,
          },
          { id: 456 },
        ];
        expectedResult = service.addEstadoCivilToCollectionIfMissing(estadoCivilCollection, estadoCivil);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EstadoCivil to an array that doesn't contain it", () => {
        const estadoCivil: IEstadoCivil = { id: 123 };
        const estadoCivilCollection: IEstadoCivil[] = [{ id: 456 }];
        expectedResult = service.addEstadoCivilToCollectionIfMissing(estadoCivilCollection, estadoCivil);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(estadoCivil);
      });

      it('should add only unique EstadoCivil to an array', () => {
        const estadoCivilArray: IEstadoCivil[] = [{ id: 123 }, { id: 456 }, { id: 15521 }];
        const estadoCivilCollection: IEstadoCivil[] = [{ id: 123 }];
        expectedResult = service.addEstadoCivilToCollectionIfMissing(estadoCivilCollection, ...estadoCivilArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const estadoCivil: IEstadoCivil = { id: 123 };
        const estadoCivil2: IEstadoCivil = { id: 456 };
        expectedResult = service.addEstadoCivilToCollectionIfMissing([], estadoCivil, estadoCivil2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(estadoCivil);
        expect(expectedResult).toContain(estadoCivil2);
      });

      it('should accept null and undefined values', () => {
        const estadoCivil: IEstadoCivil = { id: 123 };
        expectedResult = service.addEstadoCivilToCollectionIfMissing([], null, estadoCivil, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(estadoCivil);
      });

      it('should return initial array if no EstadoCivil is added', () => {
        const estadoCivilCollection: IEstadoCivil[] = [{ id: 123 }];
        expectedResult = service.addEstadoCivilToCollectionIfMissing(estadoCivilCollection, undefined, null);
        expect(expectedResult).toEqual(estadoCivilCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
