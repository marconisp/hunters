import dayjs from 'dayjs/esm';
import { IDiaSemana } from 'app/entities/config/dia-semana/dia-semana.model';

export interface IPeriodoDuracao {
  id?: number;
  nome?: string;
  dataInicio?: dayjs.Dayjs;
  dataFim?: dayjs.Dayjs;
  horaInicio?: string | null;
  horaFim?: string | null;
  obs?: string | null;
  diaSemanas?: IDiaSemana[] | null;
}

export class PeriodoDuracao implements IPeriodoDuracao {
  constructor(
    public id?: number,
    public nome?: string,
    public dataInicio?: dayjs.Dayjs,
    public dataFim?: dayjs.Dayjs,
    public horaInicio?: string | null,
    public horaFim?: string | null,
    public obs?: string | null,
    public diaSemanas?: IDiaSemana[] | null
  ) {}
}

export function getPeriodoDuracaoIdentifier(periodoDuracao: IPeriodoDuracao): number | undefined {
  return periodoDuracao.id;
}
