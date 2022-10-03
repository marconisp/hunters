import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITipoReceber, TipoReceber } from '../tipo-receber.model';

import { TipoReceberService } from './tipo-receber.service';

describe('TipoReceber Service', () => {
  let service: TipoReceberService;
  let httpMock: HttpTestingController;
  let elemDefault: ITipoReceber;
  let expectedResult: ITipoReceber | ITipoReceber[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TipoReceberService);
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

    it('should create a TipoReceber', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new TipoReceber()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TipoReceber', () => {
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

    it('should partial update a TipoReceber', () => {
      const patchObject = Object.assign(
        {
          nome: 'BBBBBB',
        },
        new TipoReceber()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TipoReceber', () => {
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

    it('should delete a TipoReceber', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTipoReceberToCollectionIfMissing', () => {
      it('should add a TipoReceber to an empty array', () => {
        const tipoReceber: ITipoReceber = { id: 123 };
        expectedResult = service.addTipoReceberToCollectionIfMissing([], tipoReceber);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tipoReceber);
      });

      it('should not add a TipoReceber to an array that contains it', () => {
        const tipoReceber: ITipoReceber = { id: 123 };
        const tipoReceberCollection: ITipoReceber[] = [
          {
            ...tipoReceber,
          },
          { id: 456 },
        ];
        expectedResult = service.addTipoReceberToCollectionIfMissing(tipoReceberCollection, tipoReceber);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TipoReceber to an array that doesn't contain it", () => {
        const tipoReceber: ITipoReceber = { id: 123 };
        const tipoReceberCollection: ITipoReceber[] = [{ id: 456 }];
        expectedResult = service.addTipoReceberToCollectionIfMissing(tipoReceberCollection, tipoReceber);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tipoReceber);
      });

      it('should add only unique TipoReceber to an array', () => {
        const tipoReceberArray: ITipoReceber[] = [{ id: 123 }, { id: 456 }, { id: 61979 }];
        const tipoReceberCollection: ITipoReceber[] = [{ id: 123 }];
        expectedResult = service.addTipoReceberToCollectionIfMissing(tipoReceberCollection, ...tipoReceberArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tipoReceber: ITipoReceber = { id: 123 };
        const tipoReceber2: ITipoReceber = { id: 456 };
        expectedResult = service.addTipoReceberToCollectionIfMissing([], tipoReceber, tipoReceber2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tipoReceber);
        expect(expectedResult).toContain(tipoReceber2);
      });

      it('should accept null and undefined values', () => {
        const tipoReceber: ITipoReceber = { id: 123 };
        expectedResult = service.addTipoReceberToCollectionIfMissing([], null, tipoReceber, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tipoReceber);
      });

      it('should return initial array if no TipoReceber is added', () => {
        const tipoReceberCollection: ITipoReceber[] = [{ id: 123 }];
        expectedResult = service.addTipoReceberToCollectionIfMissing(tipoReceberCollection, undefined, null);
        expect(expectedResult).toEqual(tipoReceberCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
