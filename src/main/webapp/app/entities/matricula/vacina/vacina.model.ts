export interface IVacina {
  id?: number;
  nome?: string;
  idade?: string | null;
  obs?: string | null;
}

export class Vacina implements IVacina {
  constructor(public id?: number, public nome?: string, public idade?: string | null, public obs?: string | null) {}
}

export function getVacinaIdentifier(vacina: IVacina): number | undefined {
  return vacina.id;
}
