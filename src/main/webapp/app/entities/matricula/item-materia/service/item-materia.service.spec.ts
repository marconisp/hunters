import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IItemMateria, ItemMateria } from '../item-materia.model';

import { ItemMateriaService } from './item-materia.service';

describe('ItemMateria Service', () => {
  let service: ItemMateriaService;
  let httpMock: HttpTestingController;
  let elemDefault: IItemMateria;
  let expectedResult: IItemMateria | IItemMateria[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ItemMateriaService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nota: 'AAAAAAA',
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

    it('should create a ItemMateria', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ItemMateria()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ItemMateria', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nota: 'BBBBBB',
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

    it('should partial update a ItemMateria', () => {
      const patchObject = Object.assign(
        {
          nota: 'BBBBBB',
        },
        new ItemMateria()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ItemMateria', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nota: 'BBBBBB',
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

    it('should delete a ItemMateria', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addItemMateriaToCollectionIfMissing', () => {
      it('should add a ItemMateria to an empty array', () => {
        const itemMateria: IItemMateria = { id: 123 };
        expectedResult = service.addItemMateriaToCollectionIfMissing([], itemMateria);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(itemMateria);
      });

      it('should not add a ItemMateria to an array that contains it', () => {
        const itemMateria: IItemMateria = { id: 123 };
        const itemMateriaCollection: IItemMateria[] = [
          {
            ...itemMateria,
          },
          { id: 456 },
        ];
        expectedResult = service.addItemMateriaToCollectionIfMissing(itemMateriaCollection, itemMateria);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ItemMateria to an array that doesn't contain it", () => {
        const itemMateria: IItemMateria = { id: 123 };
        const itemMateriaCollection: IItemMateria[] = [{ id: 456 }];
        expectedResult = service.addItemMateriaToCollectionIfMissing(itemMateriaCollection, itemMateria);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(itemMateria);
      });

      it('should add only unique ItemMateria to an array', () => {
        const itemMateriaArray: IItemMateria[] = [{ id: 123 }, { id: 456 }, { id: 49602 }];
        const itemMateriaCollection: IItemMateria[] = [{ id: 123 }];
        expectedResult = service.addItemMateriaToCollectionIfMissing(itemMateriaCollection, ...itemMateriaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const itemMateria: IItemMateria = { id: 123 };
        const itemMateria2: IItemMateria = { id: 456 };
        expectedResult = service.addItemMateriaToCollectionIfMissing([], itemMateria, itemMateria2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(itemMateria);
        expect(expectedResult).toContain(itemMateria2);
      });

      it('should accept null and undefined values', () => {
        const itemMateria: IItemMateria = { id: 123 };
        expectedResult = service.addItemMateriaToCollectionIfMissing([], null, itemMateria, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(itemMateria);
      });

      it('should return initial array if no ItemMateria is added', () => {
        const itemMateriaCollection: IItemMateria[] = [{ id: 123 }];
        expectedResult = service.addItemMateriaToCollectionIfMissing(itemMateriaCollection, undefined, null);
        expect(expectedResult).toEqual(itemMateriaCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
