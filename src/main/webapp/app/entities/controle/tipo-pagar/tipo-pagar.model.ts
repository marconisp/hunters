export interface ITipoPagar {
  id?: number;
  nome?: string;
}

export class TipoPagar implements ITipoPagar {
  constructor(public id?: number, public nome?: string) {}
}

export function getTipoPagarIdentifier(tipoPagar: ITipoPagar): number | undefined {
  return tipoPagar.id;
}
