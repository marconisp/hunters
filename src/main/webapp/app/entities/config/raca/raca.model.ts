export interface IRaca {
  id?: number;
  codigo?: string;
  descricao?: string;
}

export class Raca implements IRaca {
  constructor(public id?: number, public codigo?: string, public descricao?: string) {}
}

export function getRacaIdentifier(raca: IRaca): number | undefined {
  return raca.id;
}
