import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDadosPessoais, DadosPessoais } from '../dados-pessoais.model';

import { DadosPessoaisService } from './dados-pessoais.service';

describe('DadosPessoais Service', () => {
  let service: DadosPessoaisService;
  let httpMock: HttpTestingController;
  let elemDefault: IDadosPessoais;
  let expectedResult: IDadosPessoais | IDadosPessoais[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DadosPessoaisService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nome: 'AAAAAAA',
      sobreNome: 'AAAAAAA',
      pai: 'AAAAAAA',
      mae: 'AAAAAAA',
      telefone: 'AAAAAAA',
      celular: 'AAAAAAA',
      whatsapp: 'AAAAAAA',
      email: 'AAAAAAA',
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

    it('should create a DadosPessoais', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new DadosPessoais()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DadosPessoais', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nome: 'BBBBBB',
          sobreNome: 'BBBBBB',
          pai: 'BBBBBB',
          mae: 'BBBBBB',
          telefone: 'BBBBBB',
          celular: 'BBBBBB',
          whatsapp: 'BBBBBB',
          email: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DadosPessoais', () => {
      const patchObject = Object.assign(
        {
          nome: 'BBBBBB',
          mae: 'BBBBBB',
          whatsapp: 'BBBBBB',
        },
        new DadosPessoais()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DadosPessoais', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nome: 'BBBBBB',
          sobreNome: 'BBBBBB',
          pai: 'BBBBBB',
          mae: 'BBBBBB',
          telefone: 'BBBBBB',
          celular: 'BBBBBB',
          whatsapp: 'BBBBBB',
          email: 'BBBBBB',
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

    it('should delete a DadosPessoais', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDadosPessoaisToCollectionIfMissing', () => {
      it('should add a DadosPessoais to an empty array', () => {
        const dadosPessoais: IDadosPessoais = { id: 123 };
        expectedResult = service.addDadosPessoaisToCollectionIfMissing([], dadosPessoais);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dadosPessoais);
      });

      it('should not add a DadosPessoais to an array that contains it', () => {
        const dadosPessoais: IDadosPessoais = { id: 123 };
        const dadosPessoaisCollection: IDadosPessoais[] = [
          {
            ...dadosPessoais,
          },
          { id: 456 },
        ];
        expectedResult = service.addDadosPessoaisToCollectionIfMissing(dadosPessoaisCollection, dadosPessoais);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DadosPessoais to an array that doesn't contain it", () => {
        const dadosPessoais: IDadosPessoais = { id: 123 };
        const dadosPessoaisCollection: IDadosPessoais[] = [{ id: 456 }];
        expectedResult = service.addDadosPessoaisToCollectionIfMissing(dadosPessoaisCollection, dadosPessoais);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dadosPessoais);
      });

      it('should add only unique DadosPessoais to an array', () => {
        const dadosPessoaisArray: IDadosPessoais[] = [{ id: 123 }, { id: 456 }, { id: 79580 }];
        const dadosPessoaisCollection: IDadosPessoais[] = [{ id: 123 }];
        expectedResult = service.addDadosPessoaisToCollectionIfMissing(dadosPessoaisCollection, ...dadosPessoaisArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dadosPessoais: IDadosPessoais = { id: 123 };
        const dadosPessoais2: IDadosPessoais = { id: 456 };
        expectedResult = service.addDadosPessoaisToCollectionIfMissing([], dadosPessoais, dadosPessoais2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dadosPessoais);
        expect(expectedResult).toContain(dadosPessoais2);
      });

      it('should accept null and undefined values', () => {
        const dadosPessoais: IDadosPessoais = { id: 123 };
        expectedResult = service.addDadosPessoaisToCollectionIfMissing([], null, dadosPessoais, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dadosPessoais);
      });

      it('should return initial array if no DadosPessoais is added', () => {
        const dadosPessoaisCollection: IDadosPessoais[] = [{ id: 123 }];
        expectedResult = service.addDadosPessoaisToCollectionIfMissing(dadosPessoaisCollection, undefined, null);
        expect(expectedResult).toEqual(dadosPessoaisCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
