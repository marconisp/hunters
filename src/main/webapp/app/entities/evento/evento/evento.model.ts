import { IPeriodoDuracao } from 'app/entities/config/periodo-duracao/periodo-duracao.model';
import { IEnderecoEvento } from 'app/entities/evento/endereco-evento/endereco-evento.model';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';

export interface IEvento {
  id?: number;
  nome?: string;
  descricao?: string | null;
  ativo?: boolean | null;
  obs?: string | null;
  periodoDuracao?: IPeriodoDuracao | null;
  enderecos?: IEnderecoEvento[] | null;
  dadosPessoais?: IDadosPessoais | null;
}

export class Evento implements IEvento {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string | null,
    public ativo?: boolean | null,
    public obs?: string | null,
    public periodoDuracao?: IPeriodoDuracao | null,
    public enderecos?: IEnderecoEvento[] | null,
    public dadosPessoais?: IDadosPessoais | null
  ) {
    this.ativo = this.ativo ?? false;
  }
}

export function getEventoIdentifier(evento: IEvento): number | undefined {
  return evento.id;
}
