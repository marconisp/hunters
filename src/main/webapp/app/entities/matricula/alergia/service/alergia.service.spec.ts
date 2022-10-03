import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAlergia, Alergia } from '../alergia.model';

import { AlergiaService } from './alergia.service';

describe('Alergia Service', () => {
  let service: AlergiaService;
  let httpMock: HttpTestingController;
  let elemDefault: IAlergia;
  let expectedResult: IAlergia | IAlergia[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AlergiaService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nome: 'AAAAAAA',
      sintoma: 'AAAAAAA',
      precaucoes: 'AAAAAAA',
      socorro: 'AAAAAAA',
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

    it('should create a Alergia', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Alergia()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Alergia', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nome: 'BBBBBB',
          sintoma: 'BBBBBB',
          precaucoes: 'BBBBBB',
          socorro: 'BBBBBB',
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

    it('should partial update a Alergia', () => {
      const patchObject = Object.assign(
        {
          socorro: 'BBBBBB',
          obs: 'BBBBBB',
        },
        new Alergia()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Alergia', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nome: 'BBBBBB',
          sintoma: 'BBBBBB',
          precaucoes: 'BBBBBB',
          socorro: 'BBBBBB',
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

    it('should delete a Alergia', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAlergiaToCollectionIfMissing', () => {
      it('should add a Alergia to an empty array', () => {
        const alergia: IAlergia = { id: 123 };
        expectedResult = service.addAlergiaToCollectionIfMissing([], alergia);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(alergia);
      });

      it('should not add a Alergia to an array that contains it', () => {
        const alergia: IAlergia = { id: 123 };
        const alergiaCollection: IAlergia[] = [
          {
            ...alergia,
          },
          { id: 456 },
        ];
        expectedResult = service.addAlergiaToCollectionIfMissing(alergiaCollection, alergia);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Alergia to an array that doesn't contain it", () => {
        const alergia: IAlergia = { id: 123 };
        const alergiaCollection: IAlergia[] = [{ id: 456 }];
        expectedResult = service.addAlergiaToCollectionIfMissing(alergiaCollection, alergia);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(alergia);
      });

      it('should add only unique Alergia to an array', () => {
        const alergiaArray: IAlergia[] = [{ id: 123 }, { id: 456 }, { id: 1753 }];
        const alergiaCollection: IAlergia[] = [{ id: 123 }];
        expectedResult = service.addAlergiaToCollectionIfMissing(alergiaCollection, ...alergiaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const alergia: IAlergia = { id: 123 };
        const alergia2: IAlergia = { id: 456 };
        expectedResult = service.addAlergiaToCollectionIfMissing([], alergia, alergia2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(alergia);
        expect(expectedResult).toContain(alergia2);
      });

      it('should accept null and undefined values', () => {
        const alergia: IAlergia = { id: 123 };
        expectedResult = service.addAlergiaToCollectionIfMissing([], null, alergia, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(alergia);
      });

      it('should return initial array if no Alergia is added', () => {
        const alergiaCollection: IAlergia[] = [{ id: 123 }];
        expectedResult = service.addAlergiaToCollectionIfMissing(alergiaCollection, undefined, null);
        expect(expectedResult).toEqual(alergiaCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
