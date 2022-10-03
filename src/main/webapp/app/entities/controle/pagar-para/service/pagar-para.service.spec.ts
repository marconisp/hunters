import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPagarPara, PagarPara } from '../pagar-para.model';

import { PagarParaService } from './pagar-para.service';

describe('PagarPara Service', () => {
  let service: PagarParaService;
  let httpMock: HttpTestingController;
  let elemDefault: IPagarPara;
  let expectedResult: IPagarPara | IPagarPara[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PagarParaService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nome: 'AAAAAAA',
      descricao: 'AAAAAAA',
      cnpj: false,
      documento: 'AAAAAAA',
      banco: 'AAAAAAA',
      agencia: 'AAAAAAA',
      conta: 'AAAAAAA',
      pix: 'AAAAAAA',
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

    it('should create a PagarPara', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new PagarPara()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PagarPara', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nome: 'BBBBBB',
          descricao: 'BBBBBB',
          cnpj: true,
          documento: 'BBBBBB',
          banco: 'BBBBBB',
          agencia: 'BBBBBB',
          conta: 'BBBBBB',
          pix: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PagarPara', () => {
      const patchObject = Object.assign(
        {
          descricao: 'BBBBBB',
          cnpj: true,
          documento: 'BBBBBB',
          banco: 'BBBBBB',
          conta: 'BBBBBB',
          pix: 'BBBBBB',
        },
        new PagarPara()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PagarPara', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nome: 'BBBBBB',
          descricao: 'BBBBBB',
          cnpj: true,
          documento: 'BBBBBB',
          banco: 'BBBBBB',
          agencia: 'BBBBBB',
          conta: 'BBBBBB',
          pix: 'BBBBBB',
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

    it('should delete a PagarPara', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPagarParaToCollectionIfMissing', () => {
      it('should add a PagarPara to an empty array', () => {
        const pagarPara: IPagarPara = { id: 123 };
        expectedResult = service.addPagarParaToCollectionIfMissing([], pagarPara);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pagarPara);
      });

      it('should not add a PagarPara to an array that contains it', () => {
        const pagarPara: IPagarPara = { id: 123 };
        const pagarParaCollection: IPagarPara[] = [
          {
            ...pagarPara,
          },
          { id: 456 },
        ];
        expectedResult = service.addPagarParaToCollectionIfMissing(pagarParaCollection, pagarPara);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PagarPara to an array that doesn't contain it", () => {
        const pagarPara: IPagarPara = { id: 123 };
        const pagarParaCollection: IPagarPara[] = [{ id: 456 }];
        expectedResult = service.addPagarParaToCollectionIfMissing(pagarParaCollection, pagarPara);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pagarPara);
      });

      it('should add only unique PagarPara to an array', () => {
        const pagarParaArray: IPagarPara[] = [{ id: 123 }, { id: 456 }, { id: 70698 }];
        const pagarParaCollection: IPagarPara[] = [{ id: 123 }];
        expectedResult = service.addPagarParaToCollectionIfMissing(pagarParaCollection, ...pagarParaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pagarPara: IPagarPara = { id: 123 };
        const pagarPara2: IPagarPara = { id: 456 };
        expectedResult = service.addPagarParaToCollectionIfMissing([], pagarPara, pagarPara2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pagarPara);
        expect(expectedResult).toContain(pagarPara2);
      });

      it('should accept null and undefined values', () => {
        const pagarPara: IPagarPara = { id: 123 };
        expectedResult = service.addPagarParaToCollectionIfMissing([], null, pagarPara, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pagarPara);
      });

      it('should return initial array if no PagarPara is added', () => {
        const pagarParaCollection: IPagarPara[] = [{ id: 123 }];
        expectedResult = service.addPagarParaToCollectionIfMissing(pagarParaCollection, undefined, null);
        expect(expectedResult).toEqual(pagarParaCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
