export interface IReceberDe {
  id?: number;
  nome?: string;
  descricao?: string | null;
  cnpj?: boolean | null;
  documento?: string | null;
}

export class ReceberDe implements IReceberDe {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string | null,
    public cnpj?: boolean | null,
    public documento?: string | null
  ) {
    this.cnpj = this.cnpj ?? false;
  }
}

export function getReceberDeIdentifier(receberDe: IReceberDe): number | undefined {
  return receberDe.id;
}
