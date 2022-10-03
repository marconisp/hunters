import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDiaSemana, DiaSemana } from '../dia-semana.model';

import { DiaSemanaService } from './dia-semana.service';

describe('DiaSemana Service', () => {
  let service: DiaSemanaService;
  let httpMock: HttpTestingController;
  let elemDefault: IDiaSemana;
  let expectedResult: IDiaSemana | IDiaSemana[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DiaSemanaService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nome: 'AAAAAAA',
      obs: 'AAAAAAA',
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

    it('should create a DiaSemana', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new DiaSemana()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DiaSemana', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nome: 'BBBBBB',
          obs: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DiaSemana', () => {
      const patchObject = Object.assign({}, new DiaSemana());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DiaSemana', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nome: 'BBBBBB',
          obs: 'BBBBBB',
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

    it('should delete a DiaSemana', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDiaSemanaToCollectionIfMissing', () => {
      it('should add a DiaSemana to an empty array', () => {
        const diaSemana: IDiaSemana = { id: 123 };
        expectedResult = service.addDiaSemanaToCollectionIfMissing([], diaSemana);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(diaSemana);
      });

      it('should not add a DiaSemana to an array that contains it', () => {
        const diaSemana: IDiaSemana = { id: 123 };
        const diaSemanaCollection: IDiaSemana[] = [
          {
            ...diaSemana,
          },
          { id: 456 },
        ];
        expectedResult = service.addDiaSemanaToCollectionIfMissing(diaSemanaCollection, diaSemana);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DiaSemana to an array that doesn't contain it", () => {
        const diaSemana: IDiaSemana = { id: 123 };
        const diaSemanaCollection: IDiaSemana[] = [{ id: 456 }];
        expectedResult = service.addDiaSemanaToCollectionIfMissing(diaSemanaCollection, diaSemana);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(diaSemana);
      });

      it('should add only unique DiaSemana to an array', () => {
        const diaSemanaArray: IDiaSemana[] = [{ id: 123 }, { id: 456 }, { id: 44454 }];
        const diaSemanaCollection: IDiaSemana[] = [{ id: 123 }];
        expectedResult = service.addDiaSemanaToCollectionIfMissing(diaSemanaCollection, ...diaSemanaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const diaSemana: IDiaSemana = { id: 123 };
        const diaSemana2: IDiaSemana = { id: 456 };
        expectedResult = service.addDiaSemanaToCollectionIfMissing([], diaSemana, diaSemana2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(diaSemana);
        expect(expectedResult).toContain(diaSemana2);
      });

      it('should accept null and undefined values', () => {
        const diaSemana: IDiaSemana = { id: 123 };
        expectedResult = service.addDiaSemanaToCollectionIfMissing([], null, diaSemana, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(diaSemana);
      });

      it('should return initial array if no DiaSemana is added', () => {
        const diaSemanaCollection: IDiaSemana[] = [{ id: 123 }];
        expectedResult = service.addDiaSemanaToCollectionIfMissing(diaSemanaCollection, undefined, null);
        expect(expectedResult).toEqual(diaSemanaCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
