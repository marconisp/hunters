import dayjs from 'dayjs/esm';

export interface IFiltroReceber {
  dataInicio?: dayjs.Dayjs;
  dataFim?: dayjs.Dayjs;
  tipo?: string | null;
  transacao?: string | null;
  tipoReceber?: string | null;
  receberDe?: string | null;
  status?: string | null;
}

export class FiltroReceber implements IFiltroReceber {
  constructor(
    public data?: dayjs.Dayjs,
    public dataFim?: dayjs.Dayjs,
    public tipo?: string | null,
    public transacao?: string | null,
    public tipoReceber?: string | null,
    public receberDe?: string | null,
    public status?: string | null
  ) {}
}
