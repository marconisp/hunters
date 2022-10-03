import { IFotoProduto } from 'app/entities/foto/foto-produto/foto-produto.model';

export interface IProduto {
  id?: number;
  nome?: string;
  descricao?: string | null;
  unidade?: string | null;
  peso?: string | null;
  fotoProdutos?: IFotoProduto[] | null;
}

export class Produto implements IProduto {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string | null,
    public unidade?: string | null,
    public peso?: string | null,
    public fotoProdutos?: IFotoProduto[] | null
  ) {}
}

export function getProdutoIdentifier(produto: IProduto): number | undefined {
  return produto.id;
}
