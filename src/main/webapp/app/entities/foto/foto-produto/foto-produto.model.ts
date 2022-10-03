import { IProduto } from 'app/entities/controle/produto/produto.model';

export interface IFotoProduto {
  id?: number;
  conteudoContentType?: string;
  conteudo?: string;
  produto?: IProduto | null;
}

export class FotoProduto implements IFotoProduto {
  constructor(public id?: number, public conteudoContentType?: string, public conteudo?: string, public produto?: IProduto | null) {}
}

export function getFotoProdutoIdentifier(fotoProduto: IFotoProduto): number | undefined {
  return fotoProduto.id;
}
