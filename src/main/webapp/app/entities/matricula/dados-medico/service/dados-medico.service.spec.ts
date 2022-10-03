import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { Pressao } from 'app/entities/enumerations/pressao.model';
import { Coracao } from 'app/entities/enumerations/coracao.model';
import { IDadosMedico, DadosMedico } from '../dados-medico.model';

import { DadosMedicoService } from './dados-medico.service';

describe('DadosMedico Service', () => {
  let service: DadosMedicoService;
  let httpMock: HttpTestingController;
  let elemDefault: IDadosMedico;
  let expectedResult: IDadosMedico | IDadosMedico[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DadosMedicoService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      data: currentDate,
      peso: 0,
      altura: 0,
      pressao: Pressao.BAIXA,
      coracao: Coracao.NORMAL,
      medicacao: 'AAAAAAA',
      obs: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          data: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a DadosMedico', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          data: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          data: currentDate,
        },
        returnedFromService
      );

      service.create(new DadosMedico()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DadosMedico', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          data: currentDate.format(DATE_FORMAT),
          peso: 1,
          altura: 1,
          pressao: 'BBBBBB',
          coracao: 'BBBBBB',
          medicacao: 'BBBBBB',
          obs: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          data: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DadosMedico', () => {
      const patchObject = Object.assign(
        {
          altura: 1,
          medicacao: 'BBBBBB',
          obs: 'BBBBBB',
        },
        new DadosMedico()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          data: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DadosMedico', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          data: currentDate.format(DATE_FORMAT),
          peso: 1,
          altura: 1,
          pressao: 'BBBBBB',
          coracao: 'BBBBBB',
          medicacao: 'BBBBBB',
          obs: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          data: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a DadosMedico', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDadosMedicoToCollectionIfMissing', () => {
      it('should add a DadosMedico to an empty array', () => {
        const dadosMedico: IDadosMedico = { id: 123 };
        expectedResult = service.addDadosMedicoToCollectionIfMissing([], dadosMedico);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dadosMedico);
      });

      it('should not add a DadosMedico to an array that contains it', () => {
        const dadosMedico: IDadosMedico = { id: 123 };
        const dadosMedicoCollection: IDadosMedico[] = [
          {
            ...dadosMedico,
          },
          { id: 456 },
        ];
        expectedResult = service.addDadosMedicoToCollectionIfMissing(dadosMedicoCollection, dadosMedico);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DadosMedico to an array that doesn't contain it", () => {
        const dadosMedico: IDadosMedico = { id: 123 };
        const dadosMedicoCollection: IDadosMedico[] = [{ id: 456 }];
        expectedResult = service.addDadosMedicoToCollectionIfMissing(dadosMedicoCollection, dadosMedico);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dadosMedico);
      });

      it('should add only unique DadosMedico to an array', () => {
        const dadosMedicoArray: IDadosMedico[] = [{ id: 123 }, { id: 456 }, { id: 66853 }];
        const dadosMedicoCollection: IDadosMedico[] = [{ id: 123 }];
        expectedResult = service.addDadosMedicoToCollectionIfMissing(dadosMedicoCollection, ...dadosMedicoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dadosMedico: IDadosMedico = { id: 123 };
        const dadosMedico2: IDadosMedico = { id: 456 };
        expectedResult = service.addDadosMedicoToCollectionIfMissing([], dadosMedico, dadosMedico2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dadosMedico);
        expect(expectedResult).toContain(dadosMedico2);
      });

      it('should accept null and undefined values', () => {
        const dadosMedico: IDadosMedico = { id: 123 };
        expectedResult = service.addDadosMedicoToCollectionIfMissing([], null, dadosMedico, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dadosMedico);
      });

      it('should return initial array if no DadosMedico is added', () => {
        const dadosMedicoCollection: IDadosMedico[] = [{ id: 123 }];
        expectedResult = service.addDadosMedicoToCollectionIfMissing(dadosMedicoCollection, undefined, null);
        expect(expectedResult).toEqual(dadosMedicoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
