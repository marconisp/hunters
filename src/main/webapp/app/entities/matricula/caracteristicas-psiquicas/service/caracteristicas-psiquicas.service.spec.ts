import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICaracteristicasPsiquicas, CaracteristicasPsiquicas } from '../caracteristicas-psiquicas.model';

import { CaracteristicasPsiquicasService } from './caracteristicas-psiquicas.service';

describe('CaracteristicasPsiquicas Service', () => {
  let service: CaracteristicasPsiquicasService;
  let httpMock: HttpTestingController;
  let elemDefault: ICaracteristicasPsiquicas;
  let expectedResult: ICaracteristicasPsiquicas | ICaracteristicasPsiquicas[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CaracteristicasPsiquicasService);
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

    it('should create a CaracteristicasPsiquicas', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new CaracteristicasPsiquicas()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CaracteristicasPsiquicas', () => {
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

    it('should partial update a CaracteristicasPsiquicas', () => {
      const patchObject = Object.assign({}, new CaracteristicasPsiquicas());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CaracteristicasPsiquicas', () => {
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

    it('should delete a CaracteristicasPsiquicas', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCaracteristicasPsiquicasToCollectionIfMissing', () => {
      it('should add a CaracteristicasPsiquicas to an empty array', () => {
        const caracteristicasPsiquicas: ICaracteristicasPsiquicas = { id: 123 };
        expectedResult = service.addCaracteristicasPsiquicasToCollectionIfMissing([], caracteristicasPsiquicas);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(caracteristicasPsiquicas);
      });

      it('should not add a CaracteristicasPsiquicas to an array that contains it', () => {
        const caracteristicasPsiquicas: ICaracteristicasPsiquicas = { id: 123 };
        const caracteristicasPsiquicasCollection: ICaracteristicasPsiquicas[] = [
          {
            ...caracteristicasPsiquicas,
          },
          { id: 456 },
        ];
        expectedResult = service.addCaracteristicasPsiquicasToCollectionIfMissing(
          caracteristicasPsiquicasCollection,
          caracteristicasPsiquicas
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CaracteristicasPsiquicas to an array that doesn't contain it", () => {
        const caracteristicasPsiquicas: ICaracteristicasPsiquicas = { id: 123 };
        const caracteristicasPsiquicasCollection: ICaracteristicasPsiquicas[] = [{ id: 456 }];
        expectedResult = service.addCaracteristicasPsiquicasToCollectionIfMissing(
          caracteristicasPsiquicasCollection,
          caracteristicasPsiquicas
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(caracteristicasPsiquicas);
      });

      it('should add only unique CaracteristicasPsiquicas to an array', () => {
        const caracteristicasPsiquicasArray: ICaracteristicasPsiquicas[] = [{ id: 123 }, { id: 456 }, { id: 14372 }];
        const caracteristicasPsiquicasCollection: ICaracteristicasPsiquicas[] = [{ id: 123 }];
        expectedResult = service.addCaracteristicasPsiquicasToCollectionIfMissing(
          caracteristicasPsiquicasCollection,
          ...caracteristicasPsiquicasArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const caracteristicasPsiquicas: ICaracteristicasPsiquicas = { id: 123 };
        const caracteristicasPsiquicas2: ICaracteristicasPsiquicas = { id: 456 };
        expectedResult = service.addCaracteristicasPsiquicasToCollectionIfMissing([], caracteristicasPsiquicas, caracteristicasPsiquicas2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(caracteristicasPsiquicas);
        expect(expectedResult).toContain(caracteristicasPsiquicas2);
      });

      it('should accept null and undefined values', () => {
        const caracteristicasPsiquicas: ICaracteristicasPsiquicas = { id: 123 };
        expectedResult = service.addCaracteristicasPsiquicasToCollectionIfMissing([], null, caracteristicasPsiquicas, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(caracteristicasPsiquicas);
      });

      it('should return initial array if no CaracteristicasPsiquicas is added', () => {
        const caracteristicasPsiquicasCollection: ICaracteristicasPsiquicas[] = [{ id: 123 }];
        expectedResult = service.addCaracteristicasPsiquicasToCollectionIfMissing(caracteristicasPsiquicasCollection, undefined, null);
        expect(expectedResult).toEqual(caracteristicasPsiquicasCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
