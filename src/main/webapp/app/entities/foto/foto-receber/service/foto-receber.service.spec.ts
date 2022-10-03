import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFotoReceber, FotoReceber } from '../foto-receber.model';

import { FotoReceberService } from './foto-receber.service';

describe('FotoReceber Service', () => {
  let service: FotoReceberService;
  let httpMock: HttpTestingController;
  let elemDefault: IFotoReceber;
  let expectedResult: IFotoReceber | IFotoReceber[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FotoReceberService);
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

    it('should create a FotoReceber', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new FotoReceber()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FotoReceber', () => {
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

    it('should partial update a FotoReceber', () => {
      const patchObject = Object.assign(
        {
          conteudo: 'BBBBBB',
        },
        new FotoReceber()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FotoReceber', () => {
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

    it('should delete a FotoReceber', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addFotoReceberToCollectionIfMissing', () => {
      it('should add a FotoReceber to an empty array', () => {
        const fotoReceber: IFotoReceber = { id: 123 };
        expectedResult = service.addFotoReceberToCollectionIfMissing([], fotoReceber);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fotoReceber);
      });

      it('should not add a FotoReceber to an array that contains it', () => {
        const fotoReceber: IFotoReceber = { id: 123 };
        const fotoReceberCollection: IFotoReceber[] = [
          {
            ...fotoReceber,
          },
          { id: 456 },
        ];
        expectedResult = service.addFotoReceberToCollectionIfMissing(fotoReceberCollection, fotoReceber);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FotoReceber to an array that doesn't contain it", () => {
        const fotoReceber: IFotoReceber = { id: 123 };
        const fotoReceberCollection: IFotoReceber[] = [{ id: 456 }];
        expectedResult = service.addFotoReceberToCollectionIfMissing(fotoReceberCollection, fotoReceber);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fotoReceber);
      });

      it('should add only unique FotoReceber to an array', () => {
        const fotoReceberArray: IFotoReceber[] = [{ id: 123 }, { id: 456 }, { id: 13596 }];
        const fotoReceberCollection: IFotoReceber[] = [{ id: 123 }];
        expectedResult = service.addFotoReceberToCollectionIfMissing(fotoReceberCollection, ...fotoReceberArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fotoReceber: IFotoReceber = { id: 123 };
        const fotoReceber2: IFotoReceber = { id: 456 };
        expectedResult = service.addFotoReceberToCollectionIfMissing([], fotoReceber, fotoReceber2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fotoReceber);
        expect(expectedResult).toContain(fotoReceber2);
      });

      it('should accept null and undefined values', () => {
        const fotoReceber: IFotoReceber = { id: 123 };
        expectedResult = service.addFotoReceberToCollectionIfMissing([], null, fotoReceber, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fotoReceber);
      });

      it('should return initial array if no FotoReceber is added', () => {
        const fotoReceberCollection: IFotoReceber[] = [{ id: 123 }];
        expectedResult = service.addFotoReceberToCollectionIfMissing(fotoReceberCollection, undefined, null);
        expect(expectedResult).toEqual(fotoReceberCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
