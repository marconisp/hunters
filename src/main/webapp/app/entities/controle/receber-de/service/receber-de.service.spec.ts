import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IReceberDe, ReceberDe } from '../receber-de.model';

import { ReceberDeService } from './receber-de.service';

describe('ReceberDe Service', () => {
  let service: ReceberDeService;
  let httpMock: HttpTestingController;
  let elemDefault: IReceberDe;
  let expectedResult: IReceberDe | IReceberDe[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ReceberDeService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nome: 'AAAAAAA',
      descricao: 'AAAAAAA',
      cnpj: false,
      documento: 'AAAAAAA',
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

    it('should create a ReceberDe', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ReceberDe()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ReceberDe', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nome: 'BBBBBB',
          descricao: 'BBBBBB',
          cnpj: true,
          documento: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ReceberDe', () => {
      const patchObject = Object.assign({}, new ReceberDe());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ReceberDe', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nome: 'BBBBBB',
          descricao: 'BBBBBB',
          cnpj: true,
          documento: 'BBBBBB',
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

    it('should delete a ReceberDe', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addReceberDeToCollectionIfMissing', () => {
      it('should add a ReceberDe to an empty array', () => {
        const receberDe: IReceberDe = { id: 123 };
        expectedResult = service.addReceberDeToCollectionIfMissing([], receberDe);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(receberDe);
      });

      it('should not add a ReceberDe to an array that contains it', () => {
        const receberDe: IReceberDe = { id: 123 };
        const receberDeCollection: IReceberDe[] = [
          {
            ...receberDe,
          },
          { id: 456 },
        ];
        expectedResult = service.addReceberDeToCollectionIfMissing(receberDeCollection, receberDe);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ReceberDe to an array that doesn't contain it", () => {
        const receberDe: IReceberDe = { id: 123 };
        const receberDeCollection: IReceberDe[] = [{ id: 456 }];
        expectedResult = service.addReceberDeToCollectionIfMissing(receberDeCollection, receberDe);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(receberDe);
      });

      it('should add only unique ReceberDe to an array', () => {
        const receberDeArray: IReceberDe[] = [{ id: 123 }, { id: 456 }, { id: 81309 }];
        const receberDeCollection: IReceberDe[] = [{ id: 123 }];
        expectedResult = service.addReceberDeToCollectionIfMissing(receberDeCollection, ...receberDeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const receberDe: IReceberDe = { id: 123 };
        const receberDe2: IReceberDe = { id: 456 };
        expectedResult = service.addReceberDeToCollectionIfMissing([], receberDe, receberDe2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(receberDe);
        expect(expectedResult).toContain(receberDe2);
      });

      it('should accept null and undefined values', () => {
        const receberDe: IReceberDe = { id: 123 };
        expectedResult = service.addReceberDeToCollectionIfMissing([], null, receberDe, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(receberDe);
      });

      it('should return initial array if no ReceberDe is added', () => {
        const receberDeCollection: IReceberDe[] = [{ id: 123 }];
        expectedResult = service.addReceberDeToCollectionIfMissing(receberDeCollection, undefined, null);
        expect(expectedResult).toEqual(receberDeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
