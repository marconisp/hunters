import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFotoSaidaEstoque, FotoSaidaEstoque } from '../foto-saida-estoque.model';

import { FotoSaidaEstoqueService } from './foto-saida-estoque.service';

describe('FotoSaidaEstoque Service', () => {
  let service: FotoSaidaEstoqueService;
  let httpMock: HttpTestingController;
  let elemDefault: IFotoSaidaEstoque;
  let expectedResult: IFotoSaidaEstoque | IFotoSaidaEstoque[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FotoSaidaEstoqueService);
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

    it('should create a FotoSaidaEstoque', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new FotoSaidaEstoque()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FotoSaidaEstoque', () => {
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

    it('should partial update a FotoSaidaEstoque', () => {
      const patchObject = Object.assign(
        {
          conteudo: 'BBBBBB',
        },
        new FotoSaidaEstoque()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FotoSaidaEstoque', () => {
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

    it('should delete a FotoSaidaEstoque', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addFotoSaidaEstoqueToCollectionIfMissing', () => {
      it('should add a FotoSaidaEstoque to an empty array', () => {
        const fotoSaidaEstoque: IFotoSaidaEstoque = { id: 123 };
        expectedResult = service.addFotoSaidaEstoqueToCollectionIfMissing([], fotoSaidaEstoque);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fotoSaidaEstoque);
      });

      it('should not add a FotoSaidaEstoque to an array that contains it', () => {
        const fotoSaidaEstoque: IFotoSaidaEstoque = { id: 123 };
        const fotoSaidaEstoqueCollection: IFotoSaidaEstoque[] = [
          {
            ...fotoSaidaEstoque,
          },
          { id: 456 },
        ];
        expectedResult = service.addFotoSaidaEstoqueToCollectionIfMissing(fotoSaidaEstoqueCollection, fotoSaidaEstoque);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FotoSaidaEstoque to an array that doesn't contain it", () => {
        const fotoSaidaEstoque: IFotoSaidaEstoque = { id: 123 };
        const fotoSaidaEstoqueCollection: IFotoSaidaEstoque[] = [{ id: 456 }];
        expectedResult = service.addFotoSaidaEstoqueToCollectionIfMissing(fotoSaidaEstoqueCollection, fotoSaidaEstoque);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fotoSaidaEstoque);
      });

      it('should add only unique FotoSaidaEstoque to an array', () => {
        const fotoSaidaEstoqueArray: IFotoSaidaEstoque[] = [{ id: 123 }, { id: 456 }, { id: 82610 }];
        const fotoSaidaEstoqueCollection: IFotoSaidaEstoque[] = [{ id: 123 }];
        expectedResult = service.addFotoSaidaEstoqueToCollectionIfMissing(fotoSaidaEstoqueCollection, ...fotoSaidaEstoqueArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fotoSaidaEstoque: IFotoSaidaEstoque = { id: 123 };
        const fotoSaidaEstoque2: IFotoSaidaEstoque = { id: 456 };
        expectedResult = service.addFotoSaidaEstoqueToCollectionIfMissing([], fotoSaidaEstoque, fotoSaidaEstoque2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fotoSaidaEstoque);
        expect(expectedResult).toContain(fotoSaidaEstoque2);
      });

      it('should accept null and undefined values', () => {
        const fotoSaidaEstoque: IFotoSaidaEstoque = { id: 123 };
        expectedResult = service.addFotoSaidaEstoqueToCollectionIfMissing([], null, fotoSaidaEstoque, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fotoSaidaEstoque);
      });

      it('should return initial array if no FotoSaidaEstoque is added', () => {
        const fotoSaidaEstoqueCollection: IFotoSaidaEstoque[] = [{ id: 123 }];
        expectedResult = service.addFotoSaidaEstoqueToCollectionIfMissing(fotoSaidaEstoqueCollection, undefined, null);
        expect(expectedResult).toEqual(fotoSaidaEstoqueCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
