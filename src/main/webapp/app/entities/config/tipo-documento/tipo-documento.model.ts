export interface ITipoDocumento {
  id?: number;
  codigo?: string;
  nome?: string;
  descricao?: string | null;
}

export class TipoDocumento implements ITipoDocumento {
  constructor(public id?: number, public codigo?: string, public nome?: string, public descricao?: string | null) {}
}

export function getTipoDocumentoIdentifier(tipoDocumento: ITipoDocumento): number | undefined {
  return tipoDocumento.id;
}
