import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFotoExameMedico, FotoExameMedico } from '../foto-exame-medico.model';

import { FotoExameMedicoService } from './foto-exame-medico.service';

describe('FotoExameMedico Service', () => {
  let service: FotoExameMedicoService;
  let httpMock: HttpTestingController;
  let elemDefault: IFotoExameMedico;
  let expectedResult: IFotoExameMedico | IFotoExameMedico[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FotoExameMedicoService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      fotoContentType: 'image/png',
      foto: 'AAAAAAA',
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

    it('should create a FotoExameMedico', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new FotoExameMedico()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FotoExameMedico', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          foto: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FotoExameMedico', () => {
      const patchObject = Object.assign({}, new FotoExameMedico());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FotoExameMedico', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          foto: 'BBBBBB',
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

    it('should delete a FotoExameMedico', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addFotoExameMedicoToCollectionIfMissing', () => {
      it('should add a FotoExameMedico to an empty array', () => {
        const fotoExameMedico: IFotoExameMedico = { id: 123 };
        expectedResult = service.addFotoExameMedicoToCollectionIfMissing([], fotoExameMedico);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fotoExameMedico);
      });

      it('should not add a FotoExameMedico to an array that contains it', () => {
        const fotoExameMedico: IFotoExameMedico = { id: 123 };
        const fotoExameMedicoCollection: IFotoExameMedico[] = [
          {
            ...fotoExameMedico,
          },
          { id: 456 },
        ];
        expectedResult = service.addFotoExameMedicoToCollectionIfMissing(fotoExameMedicoCollection, fotoExameMedico);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FotoExameMedico to an array that doesn't contain it", () => {
        const fotoExameMedico: IFotoExameMedico = { id: 123 };
        const fotoExameMedicoCollection: IFotoExameMedico[] = [{ id: 456 }];
        expectedResult = service.addFotoExameMedicoToCollectionIfMissing(fotoExameMedicoCollection, fotoExameMedico);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fotoExameMedico);
      });

      it('should add only unique FotoExameMedico to an array', () => {
        const fotoExameMedicoArray: IFotoExameMedico[] = [{ id: 123 }, { id: 456 }, { id: 66896 }];
        const fotoExameMedicoCollection: IFotoExameMedico[] = [{ id: 123 }];
        expectedResult = service.addFotoExameMedicoToCollectionIfMissing(fotoExameMedicoCollection, ...fotoExameMedicoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fotoExameMedico: IFotoExameMedico = { id: 123 };
        const fotoExameMedico2: IFotoExameMedico = { id: 456 };
        expectedResult = service.addFotoExameMedicoToCollectionIfMissing([], fotoExameMedico, fotoExameMedico2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fotoExameMedico);
        expect(expectedResult).toContain(fotoExameMedico2);
      });

      it('should accept null and undefined values', () => {
        const fotoExameMedico: IFotoExameMedico = { id: 123 };
        expectedResult = service.addFotoExameMedicoToCollectionIfMissing([], null, fotoExameMedico, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fotoExameMedico);
      });

      it('should return initial array if no FotoExameMedico is added', () => {
        const fotoExameMedicoCollection: IFotoExameMedico[] = [{ id: 123 }];
        expectedResult = service.addFotoExameMedicoToCollectionIfMissing(fotoExameMedicoCollection, undefined, null);
        expect(expectedResult).toEqual(fotoExameMedicoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
