import { IPeriodoDuracao } from 'app/entities/config/periodo-duracao/periodo-duracao.model';

export interface IDiaSemana {
  id?: number;
  nome?: string;
  obs?: string | null;
  periodoDuracao?: IPeriodoDuracao | null;
}

export class DiaSemana implements IDiaSemana {
  constructor(public id?: number, public nome?: string, public obs?: string | null, public periodoDuracao?: IPeriodoDuracao | null) {}
}

export function getDiaSemanaIdentifier(diaSemana: IDiaSemana): number | undefined {
  return diaSemana.id;
}
