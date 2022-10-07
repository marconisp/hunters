import dayjs from 'dayjs/esm';
import { ITipoPagar } from 'app/entities/controle/tipo-pagar/tipo-pagar.model';
import { IPagarPara } from 'app/entities/controle/pagar-para/pagar-para.model';
import { StatusContaPagar } from 'app/entities/enumerations/status-conta-pagar.model';
import { ITipoTransacao } from 'app/entities/controle/tipo-transacao/tipo-transacao.model';

export interface IFiltroPagar {
  dataInicio?: dayjs.Dayjs;
  dataFim?: dayjs.Dayjs;
  tipo?: string | null;
  transacao?: ITipoTransacao | null;
  tipoPagar?: ITipoPagar | null;
  pagarPara?: IPagarPara | null;
  status?: StatusContaPagar | null;
  dadosPessoais?: string | null;
}

export class FiltroPagar implements IFiltroPagar {
  constructor(
    public data?: dayjs.Dayjs,
    public dataFim?: dayjs.Dayjs,
    public tipo?: string | null,
    public tipoPagar?: ITipoPagar | null,
    public pagarPara?: IPagarPara | null,
    public transacao?: ITipoTransacao | null,
    public status?: StatusContaPagar | null,
    public dadosPessoais?: string | null
  ) {}
}
