import dayjs from 'dayjs/esm';
import { IProduto } from 'app/entities/controle/produto/produto.model';
import { IGrupoProduto } from 'app/entities/controle/grupo-produto/grupo-produto.model';
import { ISubGrupoProduto } from 'app/entities/controle/sub-grupo-produto/sub-grupo-produto.model';

export interface IEstoque {
  id?: number;
  data?: dayjs.Dayjs;
  qtde?: number;
  valorUnitario?: number;
  valorTotal?: number;
  produto?: IProduto | null;
  grupoProduto?: IGrupoProduto | null;
  subGrupoProduto?: ISubGrupoProduto | null;
}

export class Estoque implements IEstoque {
  constructor(
    public id?: number,
    public data?: dayjs.Dayjs,
    public qtde?: number,
    public valorUnitario?: number,
    public valorTotal?: number,
    public produto?: IProduto | null,
    public grupoProduto?: IGrupoProduto | null,
    public subGrupoProduto?: ISubGrupoProduto | null
  ) {}
}

export function getEstoqueIdentifier(estoque: IEstoque): number | undefined {
  return estoque.id;
}
