export interface ITipoTransacao {
  id?: number;
  nome?: string;
}

export class TipoTransacao implements ITipoTransacao {
  constructor(public id?: number, public nome?: string) {}
}

export function getTipoTransacaoIdentifier(tipoTransacao: ITipoTransacao): number | undefined {
  return tipoTransacao.id;
}
