import { IGrupoProduto } from 'app/entities/controle/grupo-produto/grupo-produto.model';

export interface ISubGrupoProduto {
  id?: number;
  nome?: string;
  obs?: string | null;
  grupoProduto?: IGrupoProduto | null;
}

export class SubGrupoProduto implements ISubGrupoProduto {
  constructor(public id?: number, public nome?: string, public obs?: string | null, public grupoProduto?: IGrupoProduto | null) {}
}

export function getSubGrupoProdutoIdentifier(subGrupoProduto: ISubGrupoProduto): number | undefined {
  return subGrupoProduto.id;
}
