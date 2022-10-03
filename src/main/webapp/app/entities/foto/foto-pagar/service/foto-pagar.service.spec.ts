import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFotoPagar, FotoPagar } from '../foto-pagar.model';

import { FotoPagarService } from './foto-pagar.service';

describe('FotoPagar Service', () => {
  let service: FotoPagarService;
  let httpMock: HttpTestingController;
  let elemDefault: IFotoPagar;
  let expectedResult: IFotoPagar | IFotoPagar[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FotoPagarService);
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

    it('should create a FotoPagar', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new FotoPagar()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FotoPagar', () => {
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

    it('should partial update a FotoPagar', () => {
      const patchObject = Object.assign(
        {
          conteudo: 'BBBBBB',
        },
        new FotoPagar()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FotoPagar', () => {
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

    it('should delete a FotoPagar', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addFotoPagarToCollectionIfMissing', () => {
      it('should add a FotoPagar to an empty array', () => {
        const fotoPagar: IFotoPagar = { id: 123 };
        expectedResult = service.addFotoPagarToCollectionIfMissing([], fotoPagar);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fotoPagar);
      });

      it('should not add a FotoPagar to an array that contains it', () => {
        const fotoPagar: IFotoPagar = { id: 123 };
        const fotoPagarCollection: IFotoPagar[] = [
          {
            ...fotoPagar,
          },
          { id: 456 },
        ];
        expectedResult = service.addFotoPagarToCollectionIfMissing(fotoPagarCollection, fotoPagar);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FotoPagar to an array that doesn't contain it", () => {
        const fotoPagar: IFotoPagar = { id: 123 };
        const fotoPagarCollection: IFotoPagar[] = [{ id: 456 }];
        expectedResult = service.addFotoPagarToCollectionIfMissing(fotoPagarCollection, fotoPagar);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fotoPagar);
      });

      it('should add only unique FotoPagar to an array', () => {
        const fotoPagarArray: IFotoPagar[] = [{ id: 123 }, { id: 456 }, { id: 25956 }];
        const fotoPagarCollection: IFotoPagar[] = [{ id: 123 }];
        expectedResult = service.addFotoPagarToCollectionIfMissing(fotoPagarCollection, ...fotoPagarArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fotoPagar: IFotoPagar = { id: 123 };
        const fotoPagar2: IFotoPagar = { id: 456 };
        expectedResult = service.addFotoPagarToCollectionIfMissing([], fotoPagar, fotoPagar2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fotoPagar);
        expect(expectedResult).toContain(fotoPagar2);
      });

      it('should accept null and undefined values', () => {
        const fotoPagar: IFotoPagar = { id: 123 };
        expectedResult = service.addFotoPagarToCollectionIfMissing([], null, fotoPagar, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fotoPagar);
      });

      it('should return initial array if no FotoPagar is added', () => {
        const fotoPagarCollection: IFotoPagar[] = [{ id: 123 }];
        expectedResult = service.addFotoPagarToCollectionIfMissing(fotoPagarCollection, undefined, null);
        expect(expectedResult).toEqual(fotoPagarCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
