import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFotoAvatar, FotoAvatar } from '../foto-avatar.model';

import { FotoAvatarService } from './foto-avatar.service';

describe('FotoAvatar Service', () => {
  let service: FotoAvatarService;
  let httpMock: HttpTestingController;
  let elemDefault: IFotoAvatar;
  let expectedResult: IFotoAvatar | IFotoAvatar[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FotoAvatarService);
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

    it('should create a FotoAvatar', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new FotoAvatar()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FotoAvatar', () => {
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

    it('should partial update a FotoAvatar', () => {
      const patchObject = Object.assign(
        {
          conteudo: 'BBBBBB',
        },
        new FotoAvatar()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FotoAvatar', () => {
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

    it('should delete a FotoAvatar', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addFotoAvatarToCollectionIfMissing', () => {
      it('should add a FotoAvatar to an empty array', () => {
        const fotoAvatar: IFotoAvatar = { id: 123 };
        expectedResult = service.addFotoAvatarToCollectionIfMissing([], fotoAvatar);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fotoAvatar);
      });

      it('should not add a FotoAvatar to an array that contains it', () => {
        const fotoAvatar: IFotoAvatar = { id: 123 };
        const fotoAvatarCollection: IFotoAvatar[] = [
          {
            ...fotoAvatar,
          },
          { id: 456 },
        ];
        expectedResult = service.addFotoAvatarToCollectionIfMissing(fotoAvatarCollection, fotoAvatar);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FotoAvatar to an array that doesn't contain it", () => {
        const fotoAvatar: IFotoAvatar = { id: 123 };
        const fotoAvatarCollection: IFotoAvatar[] = [{ id: 456 }];
        expectedResult = service.addFotoAvatarToCollectionIfMissing(fotoAvatarCollection, fotoAvatar);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fotoAvatar);
      });

      it('should add only unique FotoAvatar to an array', () => {
        const fotoAvatarArray: IFotoAvatar[] = [{ id: 123 }, { id: 456 }, { id: 78694 }];
        const fotoAvatarCollection: IFotoAvatar[] = [{ id: 123 }];
        expectedResult = service.addFotoAvatarToCollectionIfMissing(fotoAvatarCollection, ...fotoAvatarArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fotoAvatar: IFotoAvatar = { id: 123 };
        const fotoAvatar2: IFotoAvatar = { id: 456 };
        expectedResult = service.addFotoAvatarToCollectionIfMissing([], fotoAvatar, fotoAvatar2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fotoAvatar);
        expect(expectedResult).toContain(fotoAvatar2);
      });

      it('should accept null and undefined values', () => {
        const fotoAvatar: IFotoAvatar = { id: 123 };
        expectedResult = service.addFotoAvatarToCollectionIfMissing([], null, fotoAvatar, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fotoAvatar);
      });

      it('should return initial array if no FotoAvatar is added', () => {
        const fotoAvatarCollection: IFotoAvatar[] = [{ id: 123 }];
        expectedResult = service.addFotoAvatarToCollectionIfMissing(fotoAvatarCollection, undefined, null);
        expect(expectedResult).toEqual(fotoAvatarCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
