import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IMensagem, Mensagem } from '../mensagem.model';

import { MensagemService } from './mensagem.service';

describe('Mensagem Service', () => {
  let service: MensagemService;
  let httpMock: HttpTestingController;
  let elemDefault: IMensagem;
  let expectedResult: IMensagem | IMensagem[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MensagemService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      data: currentDate,
      titulo: 'AAAAAAA',
      conteudo: 'AAAAAAA',
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

    it('should create a Mensagem', () => {
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

      service.create(new Mensagem()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Mensagem', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          data: currentDate.format(DATE_FORMAT),
          titulo: 'BBBBBB',
          conteudo: 'BBBBBB',
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

    it('should partial update a Mensagem', () => {
      const patchObject = Object.assign(
        {
          data: currentDate.format(DATE_FORMAT),
        },
        new Mensagem()
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

    it('should return a list of Mensagem', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          data: currentDate.format(DATE_FORMAT),
          titulo: 'BBBBBB',
          conteudo: 'BBBBBB',
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

    it('should delete a Mensagem', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addMensagemToCollectionIfMissing', () => {
      it('should add a Mensagem to an empty array', () => {
        const mensagem: IMensagem = { id: 123 };
        expectedResult = service.addMensagemToCollectionIfMissing([], mensagem);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mensagem);
      });

      it('should not add a Mensagem to an array that contains it', () => {
        const mensagem: IMensagem = { id: 123 };
        const mensagemCollection: IMensagem[] = [
          {
            ...mensagem,
          },
          { id: 456 },
        ];
        expectedResult = service.addMensagemToCollectionIfMissing(mensagemCollection, mensagem);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Mensagem to an array that doesn't contain it", () => {
        const mensagem: IMensagem = { id: 123 };
        const mensagemCollection: IMensagem[] = [{ id: 456 }];
        expectedResult = service.addMensagemToCollectionIfMissing(mensagemCollection, mensagem);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mensagem);
      });

      it('should add only unique Mensagem to an array', () => {
        const mensagemArray: IMensagem[] = [{ id: 123 }, { id: 456 }, { id: 6570 }];
        const mensagemCollection: IMensagem[] = [{ id: 123 }];
        expectedResult = service.addMensagemToCollectionIfMissing(mensagemCollection, ...mensagemArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const mensagem: IMensagem = { id: 123 };
        const mensagem2: IMensagem = { id: 456 };
        expectedResult = service.addMensagemToCollectionIfMissing([], mensagem, mensagem2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mensagem);
        expect(expectedResult).toContain(mensagem2);
      });

      it('should accept null and undefined values', () => {
        const mensagem: IMensagem = { id: 123 };
        expectedResult = service.addMensagemToCollectionIfMissing([], null, mensagem, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mensagem);
      });

      it('should return initial array if no Mensagem is added', () => {
        const mensagemCollection: IMensagem[] = [{ id: 123 }];
        expectedResult = service.addMensagemToCollectionIfMissing(mensagemCollection, undefined, null);
        expect(expectedResult).toEqual(mensagemCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
