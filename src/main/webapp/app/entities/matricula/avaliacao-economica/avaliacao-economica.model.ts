import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';
import { SimNao } from 'app/entities/enumerations/sim-nao.model';
import { Escola } from 'app/entities/enumerations/escola.model';
import { Moradia } from 'app/entities/enumerations/moradia.model';
import { Pais } from 'app/entities/enumerations/pais.model';
import { SituacaoMoradia } from 'app/entities/enumerations/situacao-moradia.model';
import { TipoMoradia } from 'app/entities/enumerations/tipo-moradia.model';
import { FamiliaExiste } from 'app/entities/enumerations/familia-existe.model';
import { AssitenciaMedica } from 'app/entities/enumerations/assitencia-medica.model';

export interface IAvaliacaoEconomica {
  id?: number;
  trabalhoOuEstagio?: SimNao;
  vinculoEmpregaticio?: SimNao | null;
  cargoFuncao?: string | null;
  contribuiRendaFamiliar?: SimNao;
  apoioFinanceiroFamiliar?: SimNao;
  estudaAtualmente?: SimNao;
  escolaAtual?: Escola | null;
  estudouAnteriormente?: SimNao;
  escolaAnterior?: Escola | null;
  concluiAnoEscolarAnterior?: SimNao;
  repetente?: SimNao | null;
  dificuldadeAprendizado?: SimNao | null;
  dificuldadeDisciplina?: string | null;
  moraCom?: Moradia;
  pais?: Pais;
  situacaoMoradia?: SituacaoMoradia;
  tipoMoradia?: TipoMoradia;
  recebeBeneficioGoverno?: SimNao;
  tipoBeneficio?: string | null;
  familiaExiste?: FamiliaExiste;
  assitenciaMedica?: AssitenciaMedica;
  temAlergia?: SimNao;
  temProblemaSaude?: SimNao;
  tomaMedicamento?: SimNao;
  teveFratura?: SimNao;
  teveCirurgia?: SimNao;
  temDeficiencia?: SimNao;
  temAcompanhamentoMedico?: SimNao;
  dadosPessoais?: IDadosPessoais | null;
}

export class AvaliacaoEconomica implements IAvaliacaoEconomica {
  constructor(
    public id?: number,
    public trabalhoOuEstagio?: SimNao,
    public vinculoEmpregaticio?: SimNao | null,
    public cargoFuncao?: string | null,
    public contribuiRendaFamiliar?: SimNao,
    public apoioFinanceiroFamiliar?: SimNao,
    public estudaAtualmente?: SimNao,
    public escolaAtual?: Escola | null,
    public estudouAnteriormente?: SimNao,
    public escolaAnterior?: Escola | null,
    public concluiAnoEscolarAnterior?: SimNao,
    public repetente?: SimNao | null,
    public dificuldadeAprendizado?: SimNao | null,
    public dificuldadeDisciplina?: string | null,
    public moraCom?: Moradia,
    public pais?: Pais,
    public situacaoMoradia?: SituacaoMoradia,
    public tipoMoradia?: TipoMoradia,
    public recebeBeneficioGoverno?: SimNao,
    public tipoBeneficio?: string | null,
    public familiaExiste?: FamiliaExiste,
    public assitenciaMedica?: AssitenciaMedica,
    public temAlergia?: SimNao,
    public temProblemaSaude?: SimNao,
    public tomaMedicamento?: SimNao,
    public teveFratura?: SimNao,
    public teveCirurgia?: SimNao,
    public temDeficiencia?: SimNao,
    public temAcompanhamentoMedico?: SimNao,
    public dadosPessoais?: IDadosPessoais | null
  ) {}
}

export function getAvaliacaoEconomicaIdentifier(avaliacaoEconomica: IAvaliacaoEconomica): number | undefined {
  return avaliacaoEconomica.id;
}
