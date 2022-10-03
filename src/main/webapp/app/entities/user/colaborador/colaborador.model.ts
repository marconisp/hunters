import dayjs from 'dayjs/esm';
import { IAgendaColaborador } from 'app/entities/user/agenda-colaborador/agenda-colaborador.model';
import { ITipoContratacao } from 'app/entities/controle/tipo-contratacao/tipo-contratacao.model';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';

export interface IColaborador {
  id?: number;
  dataCadastro?: dayjs.Dayjs;
  dataAdmissao?: dayjs.Dayjs | null;
  dataRecisao?: dayjs.Dayjs | null;
  salario?: number | null;
  ativo?: boolean | null;
  obs?: string | null;
  agendaColaboradors?: IAgendaColaborador[] | null;
  tipoContratacaos?: ITipoContratacao[] | null;
  dadosPessoais?: IDadosPessoais | null;
}

export class Colaborador implements IColaborador {
  constructor(
    public id?: number,
    public dataCadastro?: dayjs.Dayjs,
    public dataAdmissao?: dayjs.Dayjs | null,
    public dataRecisao?: dayjs.Dayjs | null,
    public salario?: number | null,
    public ativo?: boolean | null,
    public obs?: string | null,
    public agendaColaboradors?: IAgendaColaborador[] | null,
    public tipoContratacaos?: ITipoContratacao[] | null,
    public dadosPessoais?: IDadosPessoais | null
  ) {
    this.ativo = this.ativo ?? false;
  }
}

export function getColaboradorIdentifier(colaborador: IColaborador): number | undefined {
  return colaborador.id;
}
