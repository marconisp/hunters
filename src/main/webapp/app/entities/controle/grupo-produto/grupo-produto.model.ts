import { ISubGrupoProduto } from 'app/entities/controle/sub-grupo-produto/sub-grupo-produto.model';

export interface IGrupoProduto {
  id?: number;
  nome?: string;
  obs?: string | null;
  nomes?: ISubGrupoProduto[] | null;
}

export class GrupoProduto implements IGrupoProduto {
  constructor(public id?: number, public nome?: string, public obs?: string | null, public nomes?: ISubGrupoProduto[] | null) {}
}

export function getGrupoProdutoIdentifier(grupoProduto: IGrupoProduto): number | undefined {
  return grupoProduto.id;
}
