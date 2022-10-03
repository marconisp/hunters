import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IGrupoProduto, GrupoProduto } from '../grupo-produto.model';

import { GrupoProdutoService } from './grupo-produto.service';

describe('GrupoProduto Service', () => {
  let service: GrupoProdutoService;
  let httpMock: HttpTestingController;
  let elemDefault: IGrupoProduto;
  let expectedResult: IGrupoProduto | IGrupoProduto[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(GrupoProdutoService);
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

    it('should create a GrupoProduto', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new GrupoProduto()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a GrupoProduto', () => {
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

    it('should partial update a GrupoProduto', () => {
      const patchObject = Object.assign(
        {
          nome: 'BBBBBB',
          obs: 'BBBBBB',
        },
        new GrupoProduto()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of GrupoProduto', () => {
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

    it('should delete a GrupoProduto', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addGrupoProdutoToCollectionIfMissing', () => {
      it('should add a GrupoProduto to an empty array', () => {
        const grupoProduto: IGrupoProduto = { id: 123 };
        expectedResult = service.addGrupoProdutoToCollectionIfMissing([], grupoProduto);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(grupoProduto);
      });

      it('should not add a GrupoProduto to an array that contains it', () => {
        const grupoProduto: IGrupoProduto = { id: 123 };
        const grupoProdutoCollection: IGrupoProduto[] = [
          {
            ...grupoProduto,
          },
          { id: 456 },
        ];
        expectedResult = service.addGrupoProdutoToCollectionIfMissing(grupoProdutoCollection, grupoProduto);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a GrupoProduto to an array that doesn't contain it", () => {
        const grupoProduto: IGrupoProduto = { id: 123 };
        const grupoProdutoCollection: IGrupoProduto[] = [{ id: 456 }];
        expectedResult = service.addGrupoProdutoToCollectionIfMissing(grupoProdutoCollection, grupoProduto);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(grupoProduto);
      });

      it('should add only unique GrupoProduto to an array', () => {
        const grupoProdutoArray: IGrupoProduto[] = [{ id: 123 }, { id: 456 }, { id: 37619 }];
        const grupoProdutoCollection: IGrupoProduto[] = [{ id: 123 }];
        expectedResult = service.addGrupoProdutoToCollectionIfMissing(grupoProdutoCollection, ...grupoProdutoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const grupoProduto: IGrupoProduto = { id: 123 };
        const grupoProduto2: IGrupoProduto = { id: 456 };
        expectedResult = service.addGrupoProdutoToCollectionIfMissing([], grupoProduto, grupoProduto2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(grupoProduto);
        expect(expectedResult).toContain(grupoProduto2);
      });

      it('should accept null and undefined values', () => {
        const grupoProduto: IGrupoProduto = { id: 123 };
        expectedResult = service.addGrupoProdutoToCollectionIfMissing([], null, grupoProduto, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(grupoProduto);
      });

      it('should return initial array if no GrupoProduto is added', () => {
        const grupoProdutoCollection: IGrupoProduto[] = [{ id: 123 }];
        expectedResult = service.addGrupoProdutoToCollectionIfMissing(grupoProdutoCollection, undefined, null);
        expect(expectedResult).toEqual(grupoProdutoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
