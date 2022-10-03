import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { SimNao } from 'app/entities/enumerations/sim-nao.model';
import { Escola } from 'app/entities/enumerations/escola.model';
import { Moradia } from 'app/entities/enumerations/moradia.model';
import { Pais } from 'app/entities/enumerations/pais.model';
import { SituacaoMoradia } from 'app/entities/enumerations/situacao-moradia.model';
import { TipoMoradia } from 'app/entities/enumerations/tipo-moradia.model';
import { FamiliaExiste } from 'app/entities/enumerations/familia-existe.model';
import { AssitenciaMedica } from 'app/entities/enumerations/assitencia-medica.model';
import { IAvaliacaoEconomica, AvaliacaoEconomica } from '../avaliacao-economica.model';

import { AvaliacaoEconomicaService } from './avaliacao-economica.service';

describe('AvaliacaoEconomica Service', () => {
  let service: AvaliacaoEconomicaService;
  let httpMock: HttpTestingController;
  let elemDefault: IAvaliacaoEconomica;
  let expectedResult: IAvaliacaoEconomica | IAvaliacaoEconomica[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AvaliacaoEconomicaService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      trabalhoOuEstagio: SimNao.SIM,
      vinculoEmpregaticio: SimNao.SIM,
      cargoFuncao: 'AAAAAAA',
      contribuiRendaFamiliar: SimNao.SIM,
      apoioFinanceiroFamiliar: SimNao.SIM,
      estudaAtualmente: SimNao.SIM,
      escolaAtual: Escola.PUBLICA,
      estudouAnteriormente: SimNao.SIM,
      escolaAnterior: Escola.PUBLICA,
      concluiAnoEscolarAnterior: SimNao.SIM,
      repetente: SimNao.SIM,
      dificuldadeAprendizado: SimNao.SIM,
      dificuldadeDisciplina: 'AAAAAAA',
      moraCom: Moradia.PAI,
      pais: Pais.CASADOS,
      situacaoMoradia: SituacaoMoradia.PROPRIO,
      tipoMoradia: TipoMoradia.CASA,
      recebeBeneficioGoverno: SimNao.SIM,
      tipoBeneficio: 'AAAAAAA',
      familiaExiste: FamiliaExiste.ALCOOLISMO,
      assitenciaMedica: AssitenciaMedica.PUBLICA,
      temAlergia: SimNao.SIM,
      temProblemaSaude: SimNao.SIM,
      tomaMedicamento: SimNao.SIM,
      teveFratura: SimNao.SIM,
      teveCirurgia: SimNao.SIM,
      temDeficiencia: SimNao.SIM,
      temAcompanhamentoMedico: SimNao.SIM,
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

    it('should create a AvaliacaoEconomica', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new AvaliacaoEconomica()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AvaliacaoEconomica', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          trabalhoOuEstagio: 'BBBBBB',
          vinculoEmpregaticio: 'BBBBBB',
          cargoFuncao: 'BBBBBB',
          contribuiRendaFamiliar: 'BBBBBB',
          apoioFinanceiroFamiliar: 'BBBBBB',
          estudaAtualmente: 'BBBBBB',
          escolaAtual: 'BBBBBB',
          estudouAnteriormente: 'BBBBBB',
          escolaAnterior: 'BBBBBB',
          concluiAnoEscolarAnterior: 'BBBBBB',
          repetente: 'BBBBBB',
          dificuldadeAprendizado: 'BBBBBB',
          dificuldadeDisciplina: 'BBBBBB',
          moraCom: 'BBBBBB',
          pais: 'BBBBBB',
          situacaoMoradia: 'BBBBBB',
          tipoMoradia: 'BBBBBB',
          recebeBeneficioGoverno: 'BBBBBB',
          tipoBeneficio: 'BBBBBB',
          familiaExiste: 'BBBBBB',
          assitenciaMedica: 'BBBBBB',
          temAlergia: 'BBBBBB',
          temProblemaSaude: 'BBBBBB',
          tomaMedicamento: 'BBBBBB',
          teveFratura: 'BBBBBB',
          teveCirurgia: 'BBBBBB',
          temDeficiencia: 'BBBBBB',
          temAcompanhamentoMedico: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AvaliacaoEconomica', () => {
      const patchObject = Object.assign(
        {
          trabalhoOuEstagio: 'BBBBBB',
          cargoFuncao: 'BBBBBB',
          contribuiRendaFamiliar: 'BBBBBB',
          estudaAtualmente: 'BBBBBB',
          escolaAtual: 'BBBBBB',
          escolaAnterior: 'BBBBBB',
          dificuldadeAprendizado: 'BBBBBB',
          dificuldadeDisciplina: 'BBBBBB',
          pais: 'BBBBBB',
          tipoMoradia: 'BBBBBB',
          familiaExiste: 'BBBBBB',
          temProblemaSaude: 'BBBBBB',
          teveCirurgia: 'BBBBBB',
          temDeficiencia: 'BBBBBB',
        },
        new AvaliacaoEconomica()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AvaliacaoEconomica', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          trabalhoOuEstagio: 'BBBBBB',
          vinculoEmpregaticio: 'BBBBBB',
          cargoFuncao: 'BBBBBB',
          contribuiRendaFamiliar: 'BBBBBB',
          apoioFinanceiroFamiliar: 'BBBBBB',
          estudaAtualmente: 'BBBBBB',
          escolaAtual: 'BBBBBB',
          estudouAnteriormente: 'BBBBBB',
          escolaAnterior: 'BBBBBB',
          concluiAnoEscolarAnterior: 'BBBBBB',
          repetente: 'BBBBBB',
          dificuldadeAprendizado: 'BBBBBB',
          dificuldadeDisciplina: 'BBBBBB',
          moraCom: 'BBBBBB',
          pais: 'BBBBBB',
          situacaoMoradia: 'BBBBBB',
          tipoMoradia: 'BBBBBB',
          recebeBeneficioGoverno: 'BBBBBB',
          tipoBeneficio: 'BBBBBB',
          familiaExiste: 'BBBBBB',
          assitenciaMedica: 'BBBBBB',
          temAlergia: 'BBBBBB',
          temProblemaSaude: 'BBBBBB',
          tomaMedicamento: 'BBBBBB',
          teveFratura: 'BBBBBB',
          teveCirurgia: 'BBBBBB',
          temDeficiencia: 'BBBBBB',
          temAcompanhamentoMedico: 'BBBBBB',
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

    it('should delete a AvaliacaoEconomica', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAvaliacaoEconomicaToCollectionIfMissing', () => {
      it('should add a AvaliacaoEconomica to an empty array', () => {
        const avaliacaoEconomica: IAvaliacaoEconomica = { id: 123 };
        expectedResult = service.addAvaliacaoEconomicaToCollectionIfMissing([], avaliacaoEconomica);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(avaliacaoEconomica);
      });

      it('should not add a AvaliacaoEconomica to an array that contains it', () => {
        const avaliacaoEconomica: IAvaliacaoEconomica = { id: 123 };
        const avaliacaoEconomicaCollection: IAvaliacaoEconomica[] = [
          {
            ...avaliacaoEconomica,
          },
          { id: 456 },
        ];
        expectedResult = service.addAvaliacaoEconomicaToCollectionIfMissing(avaliacaoEconomicaCollection, avaliacaoEconomica);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AvaliacaoEconomica to an array that doesn't contain it", () => {
        const avaliacaoEconomica: IAvaliacaoEconomica = { id: 123 };
        const avaliacaoEconomicaCollection: IAvaliacaoEconomica[] = [{ id: 456 }];
        expectedResult = service.addAvaliacaoEconomicaToCollectionIfMissing(avaliacaoEconomicaCollection, avaliacaoEconomica);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(avaliacaoEconomica);
      });

      it('should add only unique AvaliacaoEconomica to an array', () => {
        const avaliacaoEconomicaArray: IAvaliacaoEconomica[] = [{ id: 123 }, { id: 456 }, { id: 60555 }];
        const avaliacaoEconomicaCollection: IAvaliacaoEconomica[] = [{ id: 123 }];
        expectedResult = service.addAvaliacaoEconomicaToCollectionIfMissing(avaliacaoEconomicaCollection, ...avaliacaoEconomicaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const avaliacaoEconomica: IAvaliacaoEconomica = { id: 123 };
        const avaliacaoEconomica2: IAvaliacaoEconomica = { id: 456 };
        expectedResult = service.addAvaliacaoEconomicaToCollectionIfMissing([], avaliacaoEconomica, avaliacaoEconomica2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(avaliacaoEconomica);
        expect(expectedResult).toContain(avaliacaoEconomica2);
      });

      it('should accept null and undefined values', () => {
        const avaliacaoEconomica: IAvaliacaoEconomica = { id: 123 };
        expectedResult = service.addAvaliacaoEconomicaToCollectionIfMissing([], null, avaliacaoEconomica, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(avaliacaoEconomica);
      });

      it('should return initial array if no AvaliacaoEconomica is added', () => {
        const avaliacaoEconomicaCollection: IAvaliacaoEconomica[] = [{ id: 123 }];
        expectedResult = service.addAvaliacaoEconomicaToCollectionIfMissing(avaliacaoEconomicaCollection, undefined, null);
        expect(expectedResult).toEqual(avaliacaoEconomicaCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
