import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFotoProduto, FotoProduto } from '../foto-produto.model';

import { FotoProdutoService } from './foto-produto.service';

describe('FotoProduto Service', () => {
  let service: FotoProdutoService;
  let httpMock: HttpTestingController;
  let elemDefault: IFotoProduto;
  let expectedResult: IFotoProduto | IFotoProduto[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FotoProdutoService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      conteudoContentType: 'image/png',
      conteudo: 'AAAAAAA',
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

    it('should create a FotoProduto', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new FotoProduto()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FotoProduto', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          conteudo: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FotoProduto', () => {
      const patchObject = Object.assign(
        {
          conteudo: 'BBBBBB',
        },
        new FotoProduto()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FotoProduto', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          conteudo: 'BBBBBB',
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

    it('should delete a FotoProduto', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addFotoProdutoToCollectionIfMissing', () => {
      it('should add a FotoProduto to an empty array', () => {
        const fotoProduto: IFotoProduto = { id: 123 };
        expectedResult = service.addFotoProdutoToCollectionIfMissing([], fotoProduto);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fotoProduto);
      });

      it('should not add a FotoProduto to an array that contains it', () => {
        const fotoProduto: IFotoProduto = { id: 123 };
        const fotoProdutoCollection: IFotoProduto[] = [
          {
            ...fotoProduto,
          },
          { id: 456 },
        ];
        expectedResult = service.addFotoProdutoToCollectionIfMissing(fotoProdutoCollection, fotoProduto);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FotoProduto to an array that doesn't contain it", () => {
        const fotoProduto: IFotoProduto = { id: 123 };
        const fotoProdutoCollection: IFotoProduto[] = [{ id: 456 }];
        expectedResult = service.addFotoProdutoToCollectionIfMissing(fotoProdutoCollection, fotoProduto);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fotoProduto);
      });

      it('should add only unique FotoProduto to an array', () => {
        const fotoProdutoArray: IFotoProduto[] = [{ id: 123 }, { id: 456 }, { id: 26753 }];
        const fotoProdutoCollection: IFotoProduto[] = [{ id: 123 }];
        expectedResult = service.addFotoProdutoToCollectionIfMissing(fotoProdutoCollection, ...fotoProdutoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fotoProduto: IFotoProduto = { id: 123 };
        const fotoProduto2: IFotoProduto = { id: 456 };
        expectedResult = service.addFotoProdutoToCollectionIfMissing([], fotoProduto, fotoProduto2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fotoProduto);
        expect(expectedResult).toContain(fotoProduto2);
      });

      it('should accept null and undefined values', () => {
        const fotoProduto: IFotoProduto = { id: 123 };
        expectedResult = service.addFotoProdutoToCollectionIfMissing([], null, fotoProduto, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fotoProduto);
      });

      it('should return initial array if no FotoProduto is added', () => {
        const fotoProdutoCollection: IFotoProduto[] = [{ id: 123 }];
        expectedResult = service.addFotoProdutoToCollectionIfMissing(fotoProdutoCollection, undefined, null);
        expect(expectedResult).toEqual(fotoProdutoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
