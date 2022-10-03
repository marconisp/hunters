import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITipoPagar, TipoPagar } from '../tipo-pagar.model';

import { TipoPagarService } from './tipo-pagar.service';

describe('TipoPagar Service', () => {
  let service: TipoPagarService;
  let httpMock: HttpTestingController;
  let elemDefault: ITipoPagar;
  let expectedResult: ITipoPagar | ITipoPagar[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TipoPagarService);
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

    it('should create a TipoPagar', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new TipoPagar()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TipoPagar', () => {
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

    it('should partial update a TipoPagar', () => {
      const patchObject = Object.assign({}, new TipoPagar());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TipoPagar', () => {
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

    it('should delete a TipoPagar', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTipoPagarToCollectionIfMissing', () => {
      it('should add a TipoPagar to an empty array', () => {
        const tipoPagar: ITipoPagar = { id: 123 };
        expectedResult = service.addTipoPagarToCollectionIfMissing([], tipoPagar);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tipoPagar);
      });

      it('should not add a TipoPagar to an array that contains it', () => {
        const tipoPagar: ITipoPagar = { id: 123 };
        const tipoPagarCollection: ITipoPagar[] = [
          {
            ...tipoPagar,
          },
          { id: 456 },
        ];
        expectedResult = service.addTipoPagarToCollectionIfMissing(tipoPagarCollection, tipoPagar);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TipoPagar to an array that doesn't contain it", () => {
        const tipoPagar: ITipoPagar = { id: 123 };
        const tipoPagarCollection: ITipoPagar[] = [{ id: 456 }];
        expectedResult = service.addTipoPagarToCollectionIfMissing(tipoPagarCollection, tipoPagar);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tipoPagar);
      });

      it('should add only unique TipoPagar to an array', () => {
        const tipoPagarArray: ITipoPagar[] = [{ id: 123 }, { id: 456 }, { id: 26244 }];
        const tipoPagarCollection: ITipoPagar[] = [{ id: 123 }];
        expectedResult = service.addTipoPagarToCollectionIfMissing(tipoPagarCollection, ...tipoPagarArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tipoPagar: ITipoPagar = { id: 123 };
        const tipoPagar2: ITipoPagar = { id: 456 };
        expectedResult = service.addTipoPagarToCollectionIfMissing([], tipoPagar, tipoPagar2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tipoPagar);
        expect(expectedResult).toContain(tipoPagar2);
      });

      it('should accept null and undefined values', () => {
        const tipoPagar: ITipoPagar = { id: 123 };
        expectedResult = service.addTipoPagarToCollectionIfMissing([], null, tipoPagar, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tipoPagar);
      });

      it('should return initial array if no TipoPagar is added', () => {
        const tipoPagarCollection: ITipoPagar[] = [{ id: 123 }];
        expectedResult = service.addTipoPagarToCollectionIfMissing(tipoPagarCollection, undefined, null);
        expect(expectedResult).toEqual(tipoPagarCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
