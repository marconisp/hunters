export interface ITipoMensagem {
  id?: number;
  codigo?: string;
  nome?: string;
  descricao?: string | null;
}

export class TipoMensagem implements ITipoMensagem {
  constructor(public id?: number, public codigo?: string, public nome?: string, public descricao?: string | null) {}
}

export function getTipoMensagemIdentifier(tipoMensagem: ITipoMensagem): number | undefined {
  return tipoMensagem.id;
}
