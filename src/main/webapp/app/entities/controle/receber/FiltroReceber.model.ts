import dayjs from 'dayjs/esm';
import { StatusContaReceber } from 'app/entities/enumerations/status-conta-receber.model';
import { ITipoReceber } from 'app/entities/controle/tipo-receber/tipo-receber.model';
import { IReceberDe } from 'app/entities/controle/receber-de/receber-de.model';
import { ITipoTransacao } from 'app/entities/controle/tipo-transacao/tipo-transacao.model';

export interface IFiltroReceber {
  dataInicio?: dayjs.Dayjs;
  dataFim?: dayjs.Dayjs;
  tipo?: string | null;
  transacao?: ITipoTransacao | null;
  tipoReceber?: ITipoReceber | null;
  receberDe?: IReceberDe | null;
  status?: StatusContaReceber | null;
  dadosPessoais?: string | null;
}

export class FiltroReceber implements IFiltroReceber {
  constructor(
    public data?: dayjs.Dayjs,
    public dataFim?: dayjs.Dayjs,
    public tipo?: string | null,
    public tipoReceber?: ITipoReceber | null,
    public receberDe?: IReceberDe | null,
    public transacao?: ITipoTransacao | null,
    public status?: StatusContaReceber | null,
    public dadosPessoais?: string | null
  ) {}
}
