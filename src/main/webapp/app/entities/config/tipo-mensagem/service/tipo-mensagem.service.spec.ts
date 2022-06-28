import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITipoMensagem, TipoMensagem } from '../tipo-mensagem.model';

import { TipoMensagemService } from './tipo-mensagem.service';

describe('TipoMensagem Service', () => {
  let service: TipoMensagemService;
  let httpMock: HttpTestingController;
  let elemDefault: ITipoMensagem;
  let expectedResult: ITipoMensagem | ITipoMensagem[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TipoMensagemService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      codigo: 'AAAAAAA',
      nome: 'AAAAAAA',
      descricao: 'AAAAAAA',
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

    it('should create a TipoMensagem', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new TipoMensagem()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TipoMensagem', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          codigo: 'BBBBBB',
          nome: 'BBBBBB',
          descricao: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TipoMensagem', () => {
      const patchObject = Object.assign(
        {
          descricao: 'BBBBBB',
        },
        new TipoMensagem()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TipoMensagem', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          codigo: 'BBBBBB',
          nome: 'BBBBBB',
          descricao: 'BBBBBB',
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

    it('should delete a TipoMensagem', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTipoMensagemToCollectionIfMissing', () => {
      it('should add a TipoMensagem to an empty array', () => {
        const tipoMensagem: ITipoMensagem = { id: 123 };
        expectedResult = service.addTipoMensagemToCollectionIfMissing([], tipoMensagem);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tipoMensagem);
      });

      it('should not add a TipoMensagem to an array that contains it', () => {
        const tipoMensagem: ITipoMensagem = { id: 123 };
        const tipoMensagemCollection: ITipoMensagem[] = [
          {
            ...tipoMensagem,
          },
          { id: 456 },
        ];
        expectedResult = service.addTipoMensagemToCollectionIfMissing(tipoMensagemCollection, tipoMensagem);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TipoMensagem to an array that doesn't contain it", () => {
        const tipoMensagem: ITipoMensagem = { id: 123 };
        const tipoMensagemCollection: ITipoMensagem[] = [{ id: 456 }];
        expectedResult = service.addTipoMensagemToCollectionIfMissing(tipoMensagemCollection, tipoMensagem);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tipoMensagem);
      });

      it('should add only unique TipoMensagem to an array', () => {
        const tipoMensagemArray: ITipoMensagem[] = [{ id: 123 }, { id: 456 }, { id: 35870 }];
        const tipoMensagemCollection: ITipoMensagem[] = [{ id: 123 }];
        expectedResult = service.addTipoMensagemToCollectionIfMissing(tipoMensagemCollection, ...tipoMensagemArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tipoMensagem: ITipoMensagem = { id: 123 };
        const tipoMensagem2: ITipoMensagem = { id: 456 };
        expectedResult = service.addTipoMensagemToCollectionIfMissing([], tipoMensagem, tipoMensagem2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tipoMensagem);
        expect(expectedResult).toContain(tipoMensagem2);
      });

      it('should accept null and undefined values', () => {
        const tipoMensagem: ITipoMensagem = { id: 123 };
        expectedResult = service.addTipoMensagemToCollectionIfMissing([], null, tipoMensagem, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tipoMensagem);
      });

      it('should return initial array if no TipoMensagem is added', () => {
        const tipoMensagemCollection: ITipoMensagem[] = [{ id: 123 }];
        expectedResult = service.addTipoMensagemToCollectionIfMissing(tipoMensagemCollection, undefined, null);
        expect(expectedResult).toEqual(tipoMensagemCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
