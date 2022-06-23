import dayjs from 'dayjs/esm';
import { ITipoMensagem } from 'app/entities/config/tipo-mensagem/tipo-mensagem.model';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';

export interface IMensagem {
  id?: number;
  data?: dayjs.Dayjs;
  titulo?: string;
  conteudo?: string;
  tipo?: ITipoMensagem;
  dadosPessoais?: IDadosPessoais | null;
}

export class Mensagem implements IMensagem {
  constructor(
    public id?: number,
    public data?: dayjs.Dayjs,
    public titulo?: string,
    public conteudo?: string,
    public tipo?: ITipoMensagem,
    public dadosPessoais?: IDadosPessoais | null
  ) {}
}

export function getMensagemIdentifier(mensagem: IMensagem): number | undefined {
  return mensagem.id;
}
