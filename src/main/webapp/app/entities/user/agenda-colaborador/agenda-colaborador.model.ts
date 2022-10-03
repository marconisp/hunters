import { IPeriodoDuracao } from 'app/entities/config/periodo-duracao/periodo-duracao.model';
import { IColaborador } from 'app/entities/user/colaborador/colaborador.model';

export interface IAgendaColaborador {
  id?: number;
  nome?: string;
  obs?: string | null;
  periodoDuracao?: IPeriodoDuracao | null;
  colaborador?: IColaborador | null;
}

export class AgendaColaborador implements IAgendaColaborador {
  constructor(
    public id?: number,
    public nome?: string,
    public obs?: string | null,
    public periodoDuracao?: IPeriodoDuracao | null,
    public colaborador?: IColaborador | null
  ) {}
}

export function getAgendaColaboradorIdentifier(agendaColaborador: IAgendaColaborador): number | undefined {
  return agendaColaborador.id;
}
