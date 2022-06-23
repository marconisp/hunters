import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IUser1, User1 } from '../user-1.model';

import { User1Service } from './user-1.service';

describe('User1 Service', () => {
  let service: User1Service;
  let httpMock: HttpTestingController;
  let elemDefault: IUser1;
  let expectedResult: IUser1 | IUser1[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(User1Service);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      firstName: 'AAAAAAA',
      lastName: 'AAAAAAA',
      email: 'AAAAAAA',
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

    it('should create a User1', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new User1()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a User1', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          firstName: 'BBBBBB',
          lastName: 'BBBBBB',
          email: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a User1', () => {
      const patchObject = Object.assign(
        {
          firstName: 'BBBBBB',
          lastName: 'BBBBBB',
          email: 'BBBBBB',
        },
        new User1()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of User1', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          firstName: 'BBBBBB',
          lastName: 'BBBBBB',
          email: 'BBBBBB',
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

    it('should delete a User1', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addUser1ToCollectionIfMissing', () => {
      it('should add a User1 to an empty array', () => {
        const user1: IUser1 = { id: 123 };
        expectedResult = service.addUser1ToCollectionIfMissing([], user1);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(user1);
      });

      it('should not add a User1 to an array that contains it', () => {
        const user1: IUser1 = { id: 123 };
        const user1Collection: IUser1[] = [
          {
            ...user1,
          },
          { id: 456 },
        ];
        expectedResult = service.addUser1ToCollectionIfMissing(user1Collection, user1);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a User1 to an array that doesn't contain it", () => {
        const user1: IUser1 = { id: 123 };
        const user1Collection: IUser1[] = [{ id: 456 }];
        expectedResult = service.addUser1ToCollectionIfMissing(user1Collection, user1);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(user1);
      });

      it('should add only unique User1 to an array', () => {
        const user1Array: IUser1[] = [{ id: 123 }, { id: 456 }, { id: 16043 }];
        const user1Collection: IUser1[] = [{ id: 123 }];
        expectedResult = service.addUser1ToCollectionIfMissing(user1Collection, ...user1Array);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const user1: IUser1 = { id: 123 };
        const user12: IUser1 = { id: 456 };
        expectedResult = service.addUser1ToCollectionIfMissing([], user1, user12);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(user1);
        expect(expectedResult).toContain(user12);
      });

      it('should accept null and undefined values', () => {
        const user1: IUser1 = { id: 123 };
        expectedResult = service.addUser1ToCollectionIfMissing([], null, user1, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(user1);
      });

      it('should return initial array if no User1 is added', () => {
        const user1Collection: IUser1[] = [{ id: 123 }];
        expectedResult = service.addUser1ToCollectionIfMissing(user1Collection, undefined, null);
        expect(expectedResult).toEqual(user1Collection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
