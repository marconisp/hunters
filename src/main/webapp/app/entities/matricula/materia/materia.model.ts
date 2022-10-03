export interface IMateria {
  id?: number;
  nome?: string;
}

export class Materia implements IMateria {
  constructor(public id?: number, public nome?: string) {}
}

export function getMateriaIdentifier(materia: IMateria): number | undefined {
  return materia.id;
}
