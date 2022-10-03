import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IColaborador, Colaborador } from '../colaborador.model';

import { ColaboradorService } from './colaborador.service';

describe('Colaborador Service', () => {
  let service: ColaboradorService;
  let httpMock: HttpTestingController;
  let elemDefault: IColaborador;
  let expectedResult: IColaborador | IColaborador[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ColaboradorService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      dataCadastro: currentDate,
      dataAdmissao: currentDate,
      dataRecisao: currentDate,
      salario: 0,
      ativo: false,
      obs: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dataCadastro: currentDate.format(DATE_FORMAT),
          dataAdmissao: currentDate.format(DATE_FORMAT),
          dataRecisao: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Colaborador', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dataCadastro: currentDate.format(DATE_FORMAT),
          dataAdmissao: currentDate.format(DATE_FORMAT),
          dataRecisao: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dataCadastro: currentDate,
          dataAdmissao: currentDate,
          dataRecisao: currentDate,
        },
        returnedFromService
      );

      service.create(new Colaborador()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Colaborador', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dataCadastro: currentDate.format(DATE_FORMAT),
          dataAdmissao: currentDate.format(DATE_FORMAT),
          dataRecisao: currentDate.format(DATE_FORMAT),
          salario: 1,
          ativo: true,
          obs: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dataCadastro: currentDate,
          dataAdmissao: currentDate,
          dataRecisao: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Colaborador', () => {
      const patchObject = Object.assign(
        {
          dataCadastro: currentDate.format(DATE_FORMAT),
          salario: 1,
          ativo: true,
        },
        new Colaborador()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dataCadastro: currentDate,
          dataAdmissao: currentDate,
          dataRecisao: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Colaborador', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dataCadastro: currentDate.format(DATE_FORMAT),
          dataAdmissao: currentDate.format(DATE_FORMAT),
          dataRecisao: currentDate.format(DATE_FORMAT),
          salario: 1,
          ativo: true,
          obs: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dataCadastro: currentDate,
          dataAdmissao: currentDate,
          dataRecisao: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Colaborador', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addColaboradorToCollectionIfMissing', () => {
      it('should add a Colaborador to an empty array', () => {
        const colaborador: IColaborador = { id: 123 };
        expectedResult = service.addColaboradorToCollectionIfMissing([], colaborador);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(colaborador);
      });

      it('should not add a Colaborador to an array that contains it', () => {
        const colaborador: IColaborador = { id: 123 };
        const colaboradorCollection: IColaborador[] = [
          {
            ...colaborador,
          },
          { id: 456 },
        ];
        expectedResult = service.addColaboradorToCollectionIfMissing(colaboradorCollection, colaborador);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Colaborador to an array that doesn't contain it", () => {
        const colaborador: IColaborador = { id: 123 };
        const colaboradorCollection: IColaborador[] = [{ id: 456 }];
        expectedResult = service.addColaboradorToCollectionIfMissing(colaboradorCollection, colaborador);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(colaborador);
      });

      it('should add only unique Colaborador to an array', () => {
        const colaboradorArray: IColaborador[] = [{ id: 123 }, { id: 456 }, { id: 62501 }];
        const colaboradorCollection: IColaborador[] = [{ id: 123 }];
        expectedResult = service.addColaboradorToCollectionIfMissing(colaboradorCollection, ...colaboradorArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const colaborador: IColaborador = { id: 123 };
        const colaborador2: IColaborador = { id: 456 };
        expectedResult = service.addColaboradorToCollectionIfMissing([], colaborador, colaborador2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(colaborador);
        expect(expectedResult).toContain(colaborador2);
      });

      it('should accept null and undefined values', () => {
        const colaborador: IColaborador = { id: 123 };
        expectedResult = service.addColaboradorToCollectionIfMissing([], null, colaborador, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(colaborador);
      });

      it('should return initial array if no Colaborador is added', () => {
        const colaboradorCollection: IColaborador[] = [{ id: 123 }];
        expectedResult = service.addColaboradorToCollectionIfMissing(colaboradorCollection, undefined, null);
        expect(expectedResult).toEqual(colaboradorCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
