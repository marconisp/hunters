export interface IEstadoCivil {
  id?: number;
  codigo?: string;
  descricao?: string;
}

export class EstadoCivil implements IEstadoCivil {
  constructor(public id?: number, public codigo?: string, public descricao?: string) {}
}

export function getEstadoCivilIdentifier(estadoCivil: IEstadoCivil): number | undefined {
  return estadoCivil.id;
}
