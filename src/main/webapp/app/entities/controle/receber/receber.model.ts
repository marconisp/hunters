import dayjs from 'dayjs/esm';
import { ITipoReceber } from 'app/entities/controle/tipo-receber/tipo-receber.model';
import { IReceberDe } from 'app/entities/controle/receber-de/receber-de.model';
import { ITipoTransacao } from 'app/entities/controle/tipo-transacao/tipo-transacao.model';
import { IFotoReceber } from 'app/entities/foto/foto-receber/foto-receber.model';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';
import { StatusContaReceber } from 'app/entities/enumerations/status-conta-receber.model';

export interface IReceber {
  id?: number;
  data?: dayjs.Dayjs;
  valor?: number;
  status?: StatusContaReceber | null;
  obs?: string | null;
  tipoReceber?: ITipoReceber | null;
  receberDe?: IReceberDe | null;
  tipoTransacao?: ITipoTransacao | null;
  fotoRecebers?: IFotoReceber[] | null;
  dadosPessoais?: IDadosPessoais | null;
}

export class Receber implements IReceber {
  constructor(
    public id?: number,
    public data?: dayjs.Dayjs,
    public valor?: number,
    public status?: StatusContaReceber | null,
    public obs?: string | null,
    public tipoReceber?: ITipoReceber | null,
    public receberDe?: IReceberDe | null,
    public tipoTransacao?: ITipoTransacao | null,
    public fotoRecebers?: IFotoReceber[] | null,
    public dadosPessoais?: IDadosPessoais | null
  ) {}
}

export function getReceberIdentifier(receber: IReceber): number | undefined {
  return receber.id;
}
