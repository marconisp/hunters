import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IReligiao, Religiao } from '../religiao.model';

import { ReligiaoService } from './religiao.service';

describe('Religiao Service', () => {
  let service: ReligiaoService;
  let httpMock: HttpTestingController;
  let elemDefault: IReligiao;
  let expectedResult: IReligiao | IReligiao[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ReligiaoService);
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

    it('should create a Religiao', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Religiao()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Religiao', () => {
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

    it('should partial update a Religiao', () => {
      const patchObject = Object.assign({}, new Religiao());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Religiao', () => {
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

    it('should delete a Religiao', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addReligiaoToCollectionIfMissing', () => {
      it('should add a Religiao to an empty array', () => {
        const religiao: IReligiao = { id: 123 };
        expectedResult = service.addReligiaoToCollectionIfMissing([], religiao);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(religiao);
      });

      it('should not add a Religiao to an array that contains it', () => {
        const religiao: IReligiao = { id: 123 };
        const religiaoCollection: IReligiao[] = [
          {
            ...religiao,
          },
          { id: 456 },
        ];
        expectedResult = service.addReligiaoToCollectionIfMissing(religiaoCollection, religiao);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Religiao to an array that doesn't contain it", () => {
        const religiao: IReligiao = { id: 123 };
        const religiaoCollection: IReligiao[] = [{ id: 456 }];
        expectedResult = service.addReligiaoToCollectionIfMissing(religiaoCollection, religiao);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(religiao);
      });

      it('should add only unique Religiao to an array', () => {
        const religiaoArray: IReligiao[] = [{ id: 123 }, { id: 456 }, { id: 98075 }];
        const religiaoCollection: IReligiao[] = [{ id: 123 }];
        expectedResult = service.addReligiaoToCollectionIfMissing(religiaoCollection, ...religiaoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const religiao: IReligiao = { id: 123 };
        const religiao2: IReligiao = { id: 456 };
        expectedResult = service.addReligiaoToCollectionIfMissing([], religiao, religiao2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(religiao);
        expect(expectedResult).toContain(religiao2);
      });

      it('should accept null and undefined values', () => {
        const religiao: IReligiao = { id: 123 };
        expectedResult = service.addReligiaoToCollectionIfMissing([], null, religiao, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(religiao);
      });

      it('should return initial array if no Religiao is added', () => {
        const religiaoCollection: IReligiao[] = [{ id: 123 }];
        expectedResult = service.addReligiaoToCollectionIfMissing(religiaoCollection, undefined, null);
        expect(expectedResult).toEqual(religiaoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
