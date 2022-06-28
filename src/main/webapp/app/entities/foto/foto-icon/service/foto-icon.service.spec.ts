import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFotoIcon, FotoIcon } from '../foto-icon.model';

import { FotoIconService } from './foto-icon.service';

describe('FotoIcon Service', () => {
  let service: FotoIconService;
  let httpMock: HttpTestingController;
  let elemDefault: IFotoIcon;
  let expectedResult: IFotoIcon | IFotoIcon[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FotoIconService);
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

    it('should create a FotoIcon', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new FotoIcon()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FotoIcon', () => {
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

    it('should partial update a FotoIcon', () => {
      const patchObject = Object.assign(
        {
          conteudo: 'BBBBBB',
        },
        new FotoIcon()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FotoIcon', () => {
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

    it('should delete a FotoIcon', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addFotoIconToCollectionIfMissing', () => {
      it('should add a FotoIcon to an empty array', () => {
        const fotoIcon: IFotoIcon = { id: 123 };
        expectedResult = service.addFotoIconToCollectionIfMissing([], fotoIcon);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fotoIcon);
      });

      it('should not add a FotoIcon to an array that contains it', () => {
        const fotoIcon: IFotoIcon = { id: 123 };
        const fotoIconCollection: IFotoIcon[] = [
          {
            ...fotoIcon,
          },
          { id: 456 },
        ];
        expectedResult = service.addFotoIconToCollectionIfMissing(fotoIconCollection, fotoIcon);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FotoIcon to an array that doesn't contain it", () => {
        const fotoIcon: IFotoIcon = { id: 123 };
        const fotoIconCollection: IFotoIcon[] = [{ id: 456 }];
        expectedResult = service.addFotoIconToCollectionIfMissing(fotoIconCollection, fotoIcon);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fotoIcon);
      });

      it('should add only unique FotoIcon to an array', () => {
        const fotoIconArray: IFotoIcon[] = [{ id: 123 }, { id: 456 }, { id: 2988 }];
        const fotoIconCollection: IFotoIcon[] = [{ id: 123 }];
        expectedResult = service.addFotoIconToCollectionIfMissing(fotoIconCollection, ...fotoIconArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fotoIcon: IFotoIcon = { id: 123 };
        const fotoIcon2: IFotoIcon = { id: 456 };
        expectedResult = service.addFotoIconToCollectionIfMissing([], fotoIcon, fotoIcon2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fotoIcon);
        expect(expectedResult).toContain(fotoIcon2);
      });

      it('should accept null and undefined values', () => {
        const fotoIcon: IFotoIcon = { id: 123 };
        expectedResult = service.addFotoIconToCollectionIfMissing([], null, fotoIcon, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fotoIcon);
      });

      it('should return initial array if no FotoIcon is added', () => {
        const fotoIconCollection: IFotoIcon[] = [{ id: 123 }];
        expectedResult = service.addFotoIconToCollectionIfMissing(fotoIconCollection, undefined, null);
        expect(expectedResult).toEqual(fotoIconCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
