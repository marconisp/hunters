import { IPeriodoDuracao } from 'app/entities/config/periodo-duracao/periodo-duracao.model';

export interface ITurma {
  id?: number;
  nome?: string;
  ano?: number;
  periodoDuracao?: IPeriodoDuracao | null;
}

export class Turma implements ITurma {
  constructor(public id?: number, public nome?: string, public ano?: number, public periodoDuracao?: IPeriodoDuracao | null) {}
}

export function getTurmaIdentifier(turma: ITurma): number | undefined {
  return turma.id;
}
