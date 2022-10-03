export interface IPagarPara {
  id?: number;
  nome?: string;
  descricao?: string | null;
  cnpj?: boolean | null;
  documento?: string | null;
  banco?: string | null;
  agencia?: string | null;
  conta?: string | null;
  pix?: string | null;
}

export class PagarPara implements IPagarPara {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string | null,
    public cnpj?: boolean | null,
    public documento?: string | null,
    public banco?: string | null,
    public agencia?: string | null,
    public conta?: string | null,
    public pix?: string | null
  ) {
    this.cnpj = this.cnpj ?? false;
  }
}

export function getPagarParaIdentifier(pagarPara: IPagarPara): number | undefined {
  return pagarPara.id;
}
