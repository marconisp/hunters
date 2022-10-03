import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISubGrupoProduto, SubGrupoProduto } from '../sub-grupo-produto.model';

import { SubGrupoProdutoService } from './sub-grupo-produto.service';

describe('SubGrupoProduto Service', () => {
  let service: SubGrupoProdutoService;
  let httpMock: HttpTestingController;
  let elemDefault: ISubGrupoProduto;
  let expectedResult: ISubGrupoProduto | ISubGrupoProduto[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SubGrupoProdutoService);
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

    it('should create a SubGrupoProduto', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new SubGrupoProduto()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SubGrupoProduto', () => {
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

    it('should partial update a SubGrupoProduto', () => {
      const patchObject = Object.assign(
        {
          nome: 'BBBBBB',
        },
        new SubGrupoProduto()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SubGrupoProduto', () => {
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

    it('should delete a SubGrupoProduto', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSubGrupoProdutoToCollectionIfMissing', () => {
      it('should add a SubGrupoProduto to an empty array', () => {
        const subGrupoProduto: ISubGrupoProduto = { id: 123 };
        expectedResult = service.addSubGrupoProdutoToCollectionIfMissing([], subGrupoProduto);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subGrupoProduto);
      });

      it('should not add a SubGrupoProduto to an array that contains it', () => {
        const subGrupoProduto: ISubGrupoProduto = { id: 123 };
        const subGrupoProdutoCollection: ISubGrupoProduto[] = [
          {
            ...subGrupoProduto,
          },
          { id: 456 },
        ];
        expectedResult = service.addSubGrupoProdutoToCollectionIfMissing(subGrupoProdutoCollection, subGrupoProduto);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SubGrupoProduto to an array that doesn't contain it", () => {
        const subGrupoProduto: ISubGrupoProduto = { id: 123 };
        const subGrupoProdutoCollection: ISubGrupoProduto[] = [{ id: 456 }];
        expectedResult = service.addSubGrupoProdutoToCollectionIfMissing(subGrupoProdutoCollection, subGrupoProduto);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subGrupoProduto);
      });

      it('should add only unique SubGrupoProduto to an array', () => {
        const subGrupoProdutoArray: ISubGrupoProduto[] = [{ id: 123 }, { id: 456 }, { id: 3112 }];
        const subGrupoProdutoCollection: ISubGrupoProduto[] = [{ id: 123 }];
        expectedResult = service.addSubGrupoProdutoToCollectionIfMissing(subGrupoProdutoCollection, ...subGrupoProdutoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const subGrupoProduto: ISubGrupoProduto = { id: 123 };
        const subGrupoProduto2: ISubGrupoProduto = { id: 456 };
        expectedResult = service.addSubGrupoProdutoToCollectionIfMissing([], subGrupoProduto, subGrupoProduto2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subGrupoProduto);
        expect(expectedResult).toContain(subGrupoProduto2);
      });

      it('should accept null and undefined values', () => {
        const subGrupoProduto: ISubGrupoProduto = { id: 123 };
        expectedResult = service.addSubGrupoProdutoToCollectionIfMissing([], null, subGrupoProduto, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subGrupoProduto);
      });

      it('should return initial array if no SubGrupoProduto is added', () => {
        const subGrupoProdutoCollection: ISubGrupoProduto[] = [{ id: 123 }];
        expectedResult = service.addSubGrupoProdutoToCollectionIfMissing(subGrupoProdutoCollection, undefined, null);
        expect(expectedResult).toEqual(subGrupoProdutoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
