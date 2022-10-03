import dayjs from 'dayjs/esm';
import { ITipoPagar } from 'app/entities/controle/tipo-pagar/tipo-pagar.model';
import { IPagarPara } from 'app/entities/controle/pagar-para/pagar-para.model';
import { ITipoTransacao } from 'app/entities/controle/tipo-transacao/tipo-transacao.model';
import { IFotoPagar } from 'app/entities/foto/foto-pagar/foto-pagar.model';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';
import { StatusContaPagar } from 'app/entities/enumerations/status-conta-pagar.model';

export interface IPagar {
  id?: number;
  data?: dayjs.Dayjs;
  valor?: number;
  status?: StatusContaPagar | null;
  obs?: string | null;
  tipoPagar?: ITipoPagar | null;
  pagarPara?: IPagarPara | null;
  tipoTransacao?: ITipoTransacao | null;
  fotoPagars?: IFotoPagar[] | null;
  dadosPessoais?: IDadosPessoais | null;
}

export class Pagar implements IPagar {
  constructor(
    public id?: number,
    public data?: dayjs.Dayjs,
    public valor?: number,
    public status?: StatusContaPagar | null,
    public obs?: string | null,
    public tipoPagar?: ITipoPagar | null,
    public pagarPara?: IPagarPara | null,
    public tipoTransacao?: ITipoTransacao | null,
    public fotoPagars?: IFotoPagar[] | null,
    public dadosPessoais?: IDadosPessoais | null
  ) {}
}

export function getPagarIdentifier(pagar: IPagar): number | undefined {
  return pagar.id;
}
