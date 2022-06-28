import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITipoPessoa, TipoPessoa } from '../tipo-pessoa.model';

import { TipoPessoaService } from './tipo-pessoa.service';

describe('TipoPessoa Service', () => {
  let service: TipoPessoaService;
  let httpMock: HttpTestingController;
  let elemDefault: ITipoPessoa;
  let expectedResult: ITipoPessoa | ITipoPessoa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TipoPessoaService);
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

    it('should create a TipoPessoa', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new TipoPessoa()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TipoPessoa', () => {
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

    it('should partial update a TipoPessoa', () => {
      const patchObject = Object.assign(
        {
          nome: 'BBBBBB',
        },
        new TipoPessoa()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TipoPessoa', () => {
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

    it('should delete a TipoPessoa', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTipoPessoaToCollectionIfMissing', () => {
      it('should add a TipoPessoa to an empty array', () => {
        const tipoPessoa: ITipoPessoa = { id: 123 };
        expectedResult = service.addTipoPessoaToCollectionIfMissing([], tipoPessoa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tipoPessoa);
      });

      it('should not add a TipoPessoa to an array that contains it', () => {
        const tipoPessoa: ITipoPessoa = { id: 123 };
        const tipoPessoaCollection: ITipoPessoa[] = [
          {
            ...tipoPessoa,
          },
          { id: 456 },
        ];
        expectedResult = service.addTipoPessoaToCollectionIfMissing(tipoPessoaCollection, tipoPessoa);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TipoPessoa to an array that doesn't contain it", () => {
        const tipoPessoa: ITipoPessoa = { id: 123 };
        const tipoPessoaCollection: ITipoPessoa[] = [{ id: 456 }];
        expectedResult = service.addTipoPessoaToCollectionIfMissing(tipoPessoaCollection, tipoPessoa);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tipoPessoa);
      });

      it('should add only unique TipoPessoa to an array', () => {
        const tipoPessoaArray: ITipoPessoa[] = [{ id: 123 }, { id: 456 }, { id: 37724 }];
        const tipoPessoaCollection: ITipoPessoa[] = [{ id: 123 }];
        expectedResult = service.addTipoPessoaToCollectionIfMissing(tipoPessoaCollection, ...tipoPessoaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tipoPessoa: ITipoPessoa = { id: 123 };
        const tipoPessoa2: ITipoPessoa = { id: 456 };
        expectedResult = service.addTipoPessoaToCollectionIfMissing([], tipoPessoa, tipoPessoa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tipoPessoa);
        expect(expectedResult).toContain(tipoPessoa2);
      });

      it('should accept null and undefined values', () => {
        const tipoPessoa: ITipoPessoa = { id: 123 };
        expectedResult = service.addTipoPessoaToCollectionIfMissing([], null, tipoPessoa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tipoPessoa);
      });

      it('should return initial array if no TipoPessoa is added', () => {
        const tipoPessoaCollection: ITipoPessoa[] = [{ id: 123 }];
        expectedResult = service.addTipoPessoaToCollectionIfMissing(tipoPessoaCollection, undefined, null);
        expect(expectedResult).toEqual(tipoPessoaCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
