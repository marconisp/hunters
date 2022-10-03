import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFotoEntradaEstoque, FotoEntradaEstoque } from '../foto-entrada-estoque.model';

import { FotoEntradaEstoqueService } from './foto-entrada-estoque.service';

describe('FotoEntradaEstoque Service', () => {
  let service: FotoEntradaEstoqueService;
  let httpMock: HttpTestingController;
  let elemDefault: IFotoEntradaEstoque;
  let expectedResult: IFotoEntradaEstoque | IFotoEntradaEstoque[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FotoEntradaEstoqueService);
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

    it('should create a FotoEntradaEstoque', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new FotoEntradaEstoque()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FotoEntradaEstoque', () => {
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

    it('should partial update a FotoEntradaEstoque', () => {
      const patchObject = Object.assign(
        {
          conteudo: 'BBBBBB',
        },
        new FotoEntradaEstoque()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FotoEntradaEstoque', () => {
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

    it('should delete a FotoEntradaEstoque', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addFotoEntradaEstoqueToCollectionIfMissing', () => {
      it('should add a FotoEntradaEstoque to an empty array', () => {
        const fotoEntradaEstoque: IFotoEntradaEstoque = { id: 123 };
        expectedResult = service.addFotoEntradaEstoqueToCollectionIfMissing([], fotoEntradaEstoque);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fotoEntradaEstoque);
      });

      it('should not add a FotoEntradaEstoque to an array that contains it', () => {
        const fotoEntradaEstoque: IFotoEntradaEstoque = { id: 123 };
        const fotoEntradaEstoqueCollection: IFotoEntradaEstoque[] = [
          {
            ...fotoEntradaEstoque,
          },
          { id: 456 },
        ];
        expectedResult = service.addFotoEntradaEstoqueToCollectionIfMissing(fotoEntradaEstoqueCollection, fotoEntradaEstoque);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FotoEntradaEstoque to an array that doesn't contain it", () => {
        const fotoEntradaEstoque: IFotoEntradaEstoque = { id: 123 };
        const fotoEntradaEstoqueCollection: IFotoEntradaEstoque[] = [{ id: 456 }];
        expectedResult = service.addFotoEntradaEstoqueToCollectionIfMissing(fotoEntradaEstoqueCollection, fotoEntradaEstoque);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fotoEntradaEstoque);
      });

      it('should add only unique FotoEntradaEstoque to an array', () => {
        const fotoEntradaEstoqueArray: IFotoEntradaEstoque[] = [{ id: 123 }, { id: 456 }, { id: 18568 }];
        const fotoEntradaEstoqueCollection: IFotoEntradaEstoque[] = [{ id: 123 }];
        expectedResult = service.addFotoEntradaEstoqueToCollectionIfMissing(fotoEntradaEstoqueCollection, ...fotoEntradaEstoqueArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fotoEntradaEstoque: IFotoEntradaEstoque = { id: 123 };
        const fotoEntradaEstoque2: IFotoEntradaEstoque = { id: 456 };
        expectedResult = service.addFotoEntradaEstoqueToCollectionIfMissing([], fotoEntradaEstoque, fotoEntradaEstoque2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fotoEntradaEstoque);
        expect(expectedResult).toContain(fotoEntradaEstoque2);
      });

      it('should accept null and undefined values', () => {
        const fotoEntradaEstoque: IFotoEntradaEstoque = { id: 123 };
        expectedResult = service.addFotoEntradaEstoqueToCollectionIfMissing([], null, fotoEntradaEstoque, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fotoEntradaEstoque);
      });

      it('should return initial array if no FotoEntradaEstoque is added', () => {
        const fotoEntradaEstoqueCollection: IFotoEntradaEstoque[] = [{ id: 123 }];
        expectedResult = service.addFotoEntradaEstoqueToCollectionIfMissing(fotoEntradaEstoqueCollection, undefined, null);
        expect(expectedResult).toEqual(fotoEntradaEstoqueCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
