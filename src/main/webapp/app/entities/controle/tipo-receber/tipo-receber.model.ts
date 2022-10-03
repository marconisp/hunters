export interface ITipoReceber {
  id?: number;
  nome?: string;
}

export class TipoReceber implements ITipoReceber {
  constructor(public id?: number, public nome?: string) {}
}

export function getTipoReceberIdentifier(tipoReceber: ITipoReceber): number | undefined {
  return tipoReceber.id;
}
