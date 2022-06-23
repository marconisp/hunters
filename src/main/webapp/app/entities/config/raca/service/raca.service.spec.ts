import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRaca, Raca } from '../raca.model';

import { RacaService } from './raca.service';

describe('Raca Service', () => {
  let service: RacaService;
  let httpMock: HttpTestingController;
  let elemDefault: IRaca;
  let expectedResult: IRaca | IRaca[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RacaService);
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

    it('should create a Raca', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Raca()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Raca', () => {
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

    it('should partial update a Raca', () => {
      const patchObject = Object.assign(
        {
          codigo: 'BBBBBB',
        },
        new Raca()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Raca', () => {
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

    it('should delete a Raca', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addRacaToCollectionIfMissing', () => {
      it('should add a Raca to an empty array', () => {
        const raca: IRaca = { id: 123 };
        expectedResult = service.addRacaToCollectionIfMissing([], raca);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(raca);
      });

      it('should not add a Raca to an array that contains it', () => {
        const raca: IRaca = { id: 123 };
        const racaCollection: IRaca[] = [
          {
            ...raca,
          },
          { id: 456 },
        ];
        expectedResult = service.addRacaToCollectionIfMissing(racaCollection, raca);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Raca to an array that doesn't contain it", () => {
        const raca: IRaca = { id: 123 };
        const racaCollection: IRaca[] = [{ id: 456 }];
        expectedResult = service.addRacaToCollectionIfMissing(racaCollection, raca);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(raca);
      });

      it('should add only unique Raca to an array', () => {
        const racaArray: IRaca[] = [{ id: 123 }, { id: 456 }, { id: 85534 }];
        const racaCollection: IRaca[] = [{ id: 123 }];
        expectedResult = service.addRacaToCollectionIfMissing(racaCollection, ...racaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const raca: IRaca = { id: 123 };
        const raca2: IRaca = { id: 456 };
        expectedResult = service.addRacaToCollectionIfMissing([], raca, raca2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(raca);
        expect(expectedResult).toContain(raca2);
      });

      it('should accept null and undefined values', () => {
        const raca: IRaca = { id: 123 };
        expectedResult = service.addRacaToCollectionIfMissing([], null, raca, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(raca);
      });

      it('should return initial array if no Raca is added', () => {
        const racaCollection: IRaca[] = [{ id: 123 }];
        expectedResult = service.addRacaToCollectionIfMissing(racaCollection, undefined, null);
        expect(expectedResult).toEqual(racaCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
