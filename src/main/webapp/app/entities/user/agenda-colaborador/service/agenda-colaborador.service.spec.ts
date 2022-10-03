import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAgendaColaborador, AgendaColaborador } from '../agenda-colaborador.model';

import { AgendaColaboradorService } from './agenda-colaborador.service';

describe('AgendaColaborador Service', () => {
  let service: AgendaColaboradorService;
  let httpMock: HttpTestingController;
  let elemDefault: IAgendaColaborador;
  let expectedResult: IAgendaColaborador | IAgendaColaborador[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AgendaColaboradorService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nome: 'AAAAAAA',
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

    it('should create a AgendaColaborador', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new AgendaColaborador()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AgendaColaborador', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nome: 'BBBBBB',
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

    it('should partial update a AgendaColaborador', () => {
      const patchObject = Object.assign(
        {
          obs: 'BBBBBB',
        },
        new AgendaColaborador()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AgendaColaborador', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nome: 'BBBBBB',
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

    it('should delete a AgendaColaborador', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAgendaColaboradorToCollectionIfMissing', () => {
      it('should add a AgendaColaborador to an empty array', () => {
        const agendaColaborador: IAgendaColaborador = { id: 123 };
        expectedResult = service.addAgendaColaboradorToCollectionIfMissing([], agendaColaborador);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(agendaColaborador);
      });

      it('should not add a AgendaColaborador to an array that contains it', () => {
        const agendaColaborador: IAgendaColaborador = { id: 123 };
        const agendaColaboradorCollection: IAgendaColaborador[] = [
          {
            ...agendaColaborador,
          },
          { id: 456 },
        ];
        expectedResult = service.addAgendaColaboradorToCollectionIfMissing(agendaColaboradorCollection, agendaColaborador);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AgendaColaborador to an array that doesn't contain it", () => {
        const agendaColaborador: IAgendaColaborador = { id: 123 };
        const agendaColaboradorCollection: IAgendaColaborador[] = [{ id: 456 }];
        expectedResult = service.addAgendaColaboradorToCollectionIfMissing(agendaColaboradorCollection, agendaColaborador);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(agendaColaborador);
      });

      it('should add only unique AgendaColaborador to an array', () => {
        const agendaColaboradorArray: IAgendaColaborador[] = [{ id: 123 }, { id: 456 }, { id: 75639 }];
        const agendaColaboradorCollection: IAgendaColaborador[] = [{ id: 123 }];
        expectedResult = service.addAgendaColaboradorToCollectionIfMissing(agendaColaboradorCollection, ...agendaColaboradorArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const agendaColaborador: IAgendaColaborador = { id: 123 };
        const agendaColaborador2: IAgendaColaborador = { id: 456 };
        expectedResult = service.addAgendaColaboradorToCollectionIfMissing([], agendaColaborador, agendaColaborador2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(agendaColaborador);
        expect(expectedResult).toContain(agendaColaborador2);
      });

      it('should accept null and undefined values', () => {
        const agendaColaborador: IAgendaColaborador = { id: 123 };
        expectedResult = service.addAgendaColaboradorToCollectionIfMissing([], null, agendaColaborador, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(agendaColaborador);
      });

      it('should return initial array if no AgendaColaborador is added', () => {
        const agendaColaboradorCollection: IAgendaColaborador[] = [{ id: 123 }];
        expectedResult = service.addAgendaColaboradorToCollectionIfMissing(agendaColaboradorCollection, undefined, null);
        expect(expectedResult).toEqual(agendaColaboradorCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
