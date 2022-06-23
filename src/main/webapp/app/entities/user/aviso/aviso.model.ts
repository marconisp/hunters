import dayjs from 'dayjs/esm';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';

export interface IAviso {
  id?: number;
  data?: dayjs.Dayjs;
  titulo?: string;
  conteudo?: string;
  dadosPessoais?: IDadosPessoais | null;
}

export class Aviso implements IAviso {
  constructor(
    public id?: number,
    public data?: dayjs.Dayjs,
    public titulo?: string,
    public conteudo?: string,
    public dadosPessoais?: IDadosPessoais | null
  ) {}
}

export function getAvisoIdentifier(aviso: IAviso): number | undefined {
  return aviso.id;
}
