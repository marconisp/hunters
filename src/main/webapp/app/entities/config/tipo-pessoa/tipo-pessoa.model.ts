export interface ITipoPessoa {
  id?: number;
  codigo?: string;
  nome?: string;
  descricao?: string | null;
}

export class TipoPessoa implements ITipoPessoa {
  constructor(public id?: number, public codigo?: string, public nome?: string, public descricao?: string | null) {}
}

export function getTipoPessoaIdentifier(tipoPessoa: ITipoPessoa): number | undefined {
  return tipoPessoa.id;
}
