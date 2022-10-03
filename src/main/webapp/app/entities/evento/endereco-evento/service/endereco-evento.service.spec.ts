import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEnderecoEvento, EnderecoEvento } from '../endereco-evento.model';

import { EnderecoEventoService } from './endereco-evento.service';

describe('EnderecoEvento Service', () => {
  let service: EnderecoEventoService;
  let httpMock: HttpTestingController;
  let elemDefault: IEnderecoEvento;
  let expectedResult: IEnderecoEvento | IEnderecoEvento[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EnderecoEventoService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      cep: 'AAAAAAA',
      logradouro: 'AAAAAAA',
      complemento: 'AAAAAAA',
      numero: 'AAAAAAA',
      bairro: 'AAAAAAA',
      cidade: 'AAAAAAA',
      uf: 'AAAAAAA',
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

    it('should create a EnderecoEvento', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new EnderecoEvento()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EnderecoEvento', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          cep: 'BBBBBB',
          logradouro: 'BBBBBB',
          complemento: 'BBBBBB',
          numero: 'BBBBBB',
          bairro: 'BBBBBB',
          cidade: 'BBBBBB',
          uf: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EnderecoEvento', () => {
      const patchObject = Object.assign(
        {
          logradouro: 'BBBBBB',
          bairro: 'BBBBBB',
          uf: 'BBBBBB',
        },
        new EnderecoEvento()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EnderecoEvento', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          cep: 'BBBBBB',
          logradouro: 'BBBBBB',
          complemento: 'BBBBBB',
          numero: 'BBBBBB',
          bairro: 'BBBBBB',
          cidade: 'BBBBBB',
          uf: 'BBBBBB',
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

    it('should delete a EnderecoEvento', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addEnderecoEventoToCollectionIfMissing', () => {
      it('should add a EnderecoEvento to an empty array', () => {
        const enderecoEvento: IEnderecoEvento = { id: 123 };
        expectedResult = service.addEnderecoEventoToCollectionIfMissing([], enderecoEvento);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(enderecoEvento);
      });

      it('should not add a EnderecoEvento to an array that contains it', () => {
        const enderecoEvento: IEnderecoEvento = { id: 123 };
        const enderecoEventoCollection: IEnderecoEvento[] = [
          {
            ...enderecoEvento,
          },
          { id: 456 },
        ];
        expectedResult = service.addEnderecoEventoToCollectionIfMissing(enderecoEventoCollection, enderecoEvento);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EnderecoEvento to an array that doesn't contain it", () => {
        const enderecoEvento: IEnderecoEvento = { id: 123 };
        const enderecoEventoCollection: IEnderecoEvento[] = [{ id: 456 }];
        expectedResult = service.addEnderecoEventoToCollectionIfMissing(enderecoEventoCollection, enderecoEvento);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(enderecoEvento);
      });

      it('should add only unique EnderecoEvento to an array', () => {
        const enderecoEventoArray: IEnderecoEvento[] = [{ id: 123 }, { id: 456 }, { id: 79642 }];
        const enderecoEventoCollection: IEnderecoEvento[] = [{ id: 123 }];
        expectedResult = service.addEnderecoEventoToCollectionIfMissing(enderecoEventoCollection, ...enderecoEventoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const enderecoEvento: IEnderecoEvento = { id: 123 };
        const enderecoEvento2: IEnderecoEvento = { id: 456 };
        expectedResult = service.addEnderecoEventoToCollectionIfMissing([], enderecoEvento, enderecoEvento2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(enderecoEvento);
        expect(expectedResult).toContain(enderecoEvento2);
      });

      it('should accept null and undefined values', () => {
        const enderecoEvento: IEnderecoEvento = { id: 123 };
        expectedResult = service.addEnderecoEventoToCollectionIfMissing([], null, enderecoEvento, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(enderecoEvento);
      });

      it('should return initial array if no EnderecoEvento is added', () => {
        const enderecoEventoCollection: IEnderecoEvento[] = [{ id: 123 }];
        expectedResult = service.addEnderecoEventoToCollectionIfMissing(enderecoEventoCollection, undefined, null);
        expect(expectedResult).toEqual(enderecoEventoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
