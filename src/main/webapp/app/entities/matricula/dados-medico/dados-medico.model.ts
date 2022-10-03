import dayjs from 'dayjs/esm';
import { IVacina } from 'app/entities/matricula/vacina/vacina.model';
import { IAlergia } from 'app/entities/matricula/alergia/alergia.model';
import { IDoenca } from 'app/entities/matricula/doenca/doenca.model';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';
import { Pressao } from 'app/entities/enumerations/pressao.model';
import { Coracao } from 'app/entities/enumerations/coracao.model';

export interface IDadosMedico {
  id?: number;
  data?: dayjs.Dayjs;
  peso?: number;
  altura?: number;
  pressao?: Pressao;
  coracao?: Coracao;
  medicacao?: string | null;
  obs?: string | null;
  vacina?: IVacina | null;
  alergia?: IAlergia | null;
  doenca?: IDoenca | null;
  dadosPessoais?: IDadosPessoais | null;
}

export class DadosMedico implements IDadosMedico {
  constructor(
    public id?: number,
    public data?: dayjs.Dayjs,
    public peso?: number,
    public altura?: number,
    public pressao?: Pressao,
    public coracao?: Coracao,
    public medicacao?: string | null,
    public obs?: string | null,
    public vacina?: IVacina | null,
    public alergia?: IAlergia | null,
    public doenca?: IDoenca | null,
    public dadosPessoais?: IDadosPessoais | null
  ) {}
}

export function getDadosMedicoIdentifier(dadosMedico: IDadosMedico): number | undefined {
  return dadosMedico.id;
}
