import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { Ensino } from 'app/entities/enumerations/ensino.model';
import { IAcompanhamentoAluno, AcompanhamentoAluno } from '../acompanhamento-aluno.model';

import { AcompanhamentoAlunoService } from './acompanhamento-aluno.service';

describe('AcompanhamentoAluno Service', () => {
  let service: AcompanhamentoAlunoService;
  let httpMock: HttpTestingController;
  let elemDefault: IAcompanhamentoAluno;
  let expectedResult: IAcompanhamentoAluno | IAcompanhamentoAluno[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AcompanhamentoAlunoService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      ano: 0,
      ensino: Ensino.FUNDAMENTAL1,
      bimestre: 0,
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

    it('should create a AcompanhamentoAluno', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new AcompanhamentoAluno()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AcompanhamentoAluno', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          ano: 1,
          ensino: 'BBBBBB',
          bimestre: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AcompanhamentoAluno', () => {
      const patchObject = Object.assign(
        {
          ano: 1,
          bimestre: 1,
        },
        new AcompanhamentoAluno()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AcompanhamentoAluno', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          ano: 1,
          ensino: 'BBBBBB',
          bimestre: 1,
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

    it('should delete a AcompanhamentoAluno', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAcompanhamentoAlunoToCollectionIfMissing', () => {
      it('should add a AcompanhamentoAluno to an empty array', () => {
        const acompanhamentoAluno: IAcompanhamentoAluno = { id: 123 };
        expectedResult = service.addAcompanhamentoAlunoToCollectionIfMissing([], acompanhamentoAluno);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(acompanhamentoAluno);
      });

      it('should not add a AcompanhamentoAluno to an array that contains it', () => {
        const acompanhamentoAluno: IAcompanhamentoAluno = { id: 123 };
        const acompanhamentoAlunoCollection: IAcompanhamentoAluno[] = [
          {
            ...acompanhamentoAluno,
          },
          { id: 456 },
        ];
        expectedResult = service.addAcompanhamentoAlunoToCollectionIfMissing(acompanhamentoAlunoCollection, acompanhamentoAluno);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AcompanhamentoAluno to an array that doesn't contain it", () => {
        const acompanhamentoAluno: IAcompanhamentoAluno = { id: 123 };
        const acompanhamentoAlunoCollection: IAcompanhamentoAluno[] = [{ id: 456 }];
        expectedResult = service.addAcompanhamentoAlunoToCollectionIfMissing(acompanhamentoAlunoCollection, acompanhamentoAluno);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(acompanhamentoAluno);
      });

      it('should add only unique AcompanhamentoAluno to an array', () => {
        const acompanhamentoAlunoArray: IAcompanhamentoAluno[] = [{ id: 123 }, { id: 456 }, { id: 46671 }];
        const acompanhamentoAlunoCollection: IAcompanhamentoAluno[] = [{ id: 123 }];
        expectedResult = service.addAcompanhamentoAlunoToCollectionIfMissing(acompanhamentoAlunoCollection, ...acompanhamentoAlunoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const acompanhamentoAluno: IAcompanhamentoAluno = { id: 123 };
        const acompanhamentoAluno2: IAcompanhamentoAluno = { id: 456 };
        expectedResult = service.addAcompanhamentoAlunoToCollectionIfMissing([], acompanhamentoAluno, acompanhamentoAluno2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(acompanhamentoAluno);
        expect(expectedResult).toContain(acompanhamentoAluno2);
      });

      it('should accept null and undefined values', () => {
        const acompanhamentoAluno: IAcompanhamentoAluno = { id: 123 };
        expectedResult = service.addAcompanhamentoAlunoToCollectionIfMissing([], null, acompanhamentoAluno, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(acompanhamentoAluno);
      });

      it('should return initial array if no AcompanhamentoAluno is added', () => {
        const acompanhamentoAlunoCollection: IAcompanhamentoAluno[] = [{ id: 123 }];
        expectedResult = service.addAcompanhamentoAlunoToCollectionIfMissing(acompanhamentoAlunoCollection, undefined, null);
        expect(expectedResult).toEqual(acompanhamentoAlunoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
